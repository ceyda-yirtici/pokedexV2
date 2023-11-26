package com.example.pokedex.data.repository

import com.example.pokedex.data.service.PokeService
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.model.PokemonList
import com.example.pokedex.domain.repository.PokeRepository
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class PokeRepositoryImpl @Inject constructor (val service: PokeService) : PokeRepository {

    override suspend fun fetchPokemonList(limit: Int, offset:Int): Resource<PokemonList> {
        return try {
            val response = service.fetchPokemonList(limit,offset)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Body boş geldi!",null)
            } else {
                Resource.error("Response successful değil!",null)
            }
        }
        catch (e: CancellationException){
            throw e
        }
        catch (e: Exception) {
            Resource.error("Bütün olay patladı!",null)
        }
    }


    override suspend fun fetchPokemonDetailById(id:Int): Resource<PokemonDetail> {
        return try {
            val response = service.fetchPokemonDetailById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Body boş geldi!",null)
            } else {
                Resource.error("Response successful değil!",null)
            }
        }
        catch (e: CancellationException){
            throw e
        }
        catch (e: Exception) {
            Resource.error("Bütün olay patladı!",null)
        }
    }

    override suspend fun fetchPokemonDetailByName(name: String): Resource<PokemonDetail> {
        return try {
            val response = service.fetchPokemonDetailByName(name)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Body boş geldi!",null)
            } else {
                Resource.error("Response successful değil!",null)
            }
        } catch (e: Exception) {
            Resource.error("Bütün olay patladı!",null)
        }
    }
}