package com.game.app_pokemon.data.remote


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val sprites: SpriteDto,
    val stats: List<StatDto>,
    val types: List<TypeDto>

)

@Serializable
data class SpriteDto(
    @SerialName("front_default") val frontDefault: String?
)

@Serializable
data class StatDto(
    @SerialName("base_stat") val baseStat: Int,
    val stat: StatInfoDto

)

@Serializable
data class TypeDto(
    val type: TypeInfoDto
)

@Serializable
data class StatInfoDto(
    val name: String
)

@Serializable
data class TypeInfoDto(
    val name: String
)