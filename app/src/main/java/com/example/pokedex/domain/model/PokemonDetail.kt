package com.example.pokedex.domain.model

import com.example.day5.model.AbilityDetails
import com.example.day5.model.CharacterType
import com.example.day5.model.DetailCharacterSprites
import com.example.day5.model.PokemonStat
import com.google.gson.annotations.SerializedName

data class PokemonDetail (

    @SerializedName("name") val name: String?,
    @SerializedName("url") val avatarUrl: String?,
    @SerializedName("types") val types: List<CharacterType>,
    @SerializedName("id") val id: Int?,
    @SerializedName("weight") val weight: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("abilities") val abilities: List<AbilityDetails>,
    @SerializedName("sprites") val sprites: DetailCharacterSprites,
    @SerializedName("stats") val stats: List<PokemonStat>,
    @SerializedName("color") val color: String?


)


