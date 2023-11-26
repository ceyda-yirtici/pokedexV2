package com.example.pokedex.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment  : Fragment() {

    private var pokemonAdapter: PokemonAdapter = PokemonAdapter()
    private val viewModel: ListViewModel by viewModels(ownerProducer = { this })
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllData(20,0)
        initView()
        initListener()
    }

    private fun initView() {

        val layoutManager = GridLayoutManager(requireContext(), spanCountPortrait)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = pokemonAdapter
    }

    private fun initListener() {
        viewModel.pokemonList.observe(viewLifecycleOwner) {
            pokemonAdapter.updatePokeList(it)

        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            it?.let {
                if (it){
                    binding.pbList.visibility = View.VISIBLE
                    binding.rvList.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                } else {
                    binding.rvList.visibility = View.VISIBLE
                    binding.pbList.visibility = View.GONE
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = it
            }
        }

        binding.swList.setOnQueryTextListener (object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    try {
                        val queryNumber = query.toInt()
                        viewModel.loadSearchDataById(queryNumber)
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    try {
                        if(query=="") viewModel.loadAllData(20,0)
                        else {
                            val queryNumber = query.toInt()
                            viewModel.loadSearchDataById(queryNumber)
                        }
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
                return true
            }
        })
    }

    companion object
    {
        const val spanCountPortrait = 3
    }



}