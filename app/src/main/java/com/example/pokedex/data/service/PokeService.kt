package com.example.pokedex.data.service

import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface PokeService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetailByName(@Path("name") name: String): Response<PokemonDetail>


    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id: Int): Response<PokemonDetail>

}

