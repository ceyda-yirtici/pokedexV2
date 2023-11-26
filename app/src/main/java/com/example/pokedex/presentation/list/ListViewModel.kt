package com.example.pokedex.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokeRepository
import com.example.pokedex.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val pokeRepository: PokeRepository
) : ViewModel() {

    private var loadingJob: Job? = null


    private val mutableList = MutableLiveData<List<PokemonDetail>>()
    val pokemonList: LiveData<List<PokemonDetail>> get() = mutableList

    private val mutableIsLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = mutableIsLoading

    private val mutableError = MutableLiveData<String?>("Enter a query")
    val error: LiveData<String?> get() = mutableError


    fun loadAllData(limit:Int, offset:Int) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            mutableIsLoading.value = true

            val resultList = mutableListOf<PokemonDetail>()

            for (i in offset until limit) {
                val result = pokeRepository.fetchPokemonDetailById(i + 1)

                when (result.status) {
                    Status.SUCCESS -> {
                        if(i==limit-1)
                            mutableIsLoading.value = false
                    }

                    Status.ERROR -> {
                        mutableError.value = result.message
                        mutableIsLoading.value = false
                    }

                    Status.LOADING -> {
                        mutableIsLoading.value = true
                    }
                }

                result.data?.let {
                    resultList.add(it)
                }
            }
            if(resultList.isNotEmpty()){
                resultList.let { mutableList.value = it }
            }
            else{
                mutableList.value = emptyList()
                mutableIsLoading.value = false
            }
        }
    }
    fun loadSearchDataById(id:Int) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(500)
            mutableIsLoading.value = true

            val resultList = mutableListOf<PokemonDetail>()

            val result = pokeRepository.fetchPokemonDetailById(id)
            when (result.status) {
                Status.SUCCESS -> {
                    mutableIsLoading.value = false
                }

                Status.ERROR -> {
                    mutableError.value = result.message
                    mutableIsLoading.value = false
                }

                Status.LOADING -> {
                    mutableIsLoading.value = true
                }
            }
            result.data?.let {
                resultList.add(it)
            }
            if(result.data!= null){
                mutableIsLoading.value = false
                resultList.let { mutableList.value = it }
            }
            else{
                mutableList.value = emptyList()
                mutableIsLoading.value = false
            }
        }
    }
}