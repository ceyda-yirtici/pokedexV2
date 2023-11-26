package com.example.pokedex.domain.repository

import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.model.PokemonList
import com.example.pokedex.utils.Resource
import dagger.Provides


interface PokeRepository {

    suspend fun fetchPokemonList(limit:Int, offset: Int): Resource<PokemonList>
    suspend fun fetchPokemonDetailByName(pokemonName:String): Resource<PokemonDetail>
    suspend fun fetchPokemonDetailById(id:Int): Resource<PokemonDetail>

}

