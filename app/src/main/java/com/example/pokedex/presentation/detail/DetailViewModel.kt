package com.example.pokedex.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.model.PokemonList
import com.example.pokedex.domain.repository.PokeRepository
import com.example.pokedex.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokeRepository: PokeRepository
): ViewModel() {

    private var loadingJob: Job? = null


    private val mutablePokemon = MutableLiveData<PokemonDetail>()
    val pokemon: LiveData<PokemonDetail> get() = mutablePokemon

    private val mutableIsLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = mutableIsLoading

    private val mutableError = MutableLiveData<String?>("Enter a query")
    val error: LiveData<String?> get() = mutableError


    fun loadData(name:String) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            mutableIsLoading.value = true
            val result = pokeRepository.fetchPokemonDetailByName(name)
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
            result.data.let { mutablePokemon.postValue(it) }
        }
    }
}