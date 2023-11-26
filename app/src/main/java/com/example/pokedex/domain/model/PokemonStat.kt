package com.example.day5.model

import com.google.gson.annotations.SerializedName


data class PokemonStat(
    @SerializedName("base_stat")  val base_stat: Int,
    @SerializedName("stat")  val stat: Stat
)

class Stat (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
