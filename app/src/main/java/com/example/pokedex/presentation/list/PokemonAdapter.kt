package com.example.pokedex.presentation.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.PokemonItemBinding
import com.example.pokedex.domain.model.ListResult
import com.example.pokedex.domain.model.PokemonDetail

class PokemonAdapter
    : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(var binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var pokeList: List<PokemonDetail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updatePokeList(item: List<PokemonDetail>) {
        item.let {
            pokeList = item
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return pokeList.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val poke = pokeList[position]
        val pokemonItem = holder.binding

        pokemonItem.tvTitle.text = poke.name
        pokemonItem.tvId.text = "#" + poke.id?.let { formatNumberToThreeDigits(it) }
        Glide.with(pokemonItem.ivPokemon).load(poke.sprites.other.official_artwork.front_default).into(pokemonItem.ivPokemon)


    }
    private fun formatNumberToThreeDigits(number: Int): String {
        return String.format("%03d", number)
    }


}