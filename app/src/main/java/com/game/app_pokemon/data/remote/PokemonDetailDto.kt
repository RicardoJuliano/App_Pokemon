package com.game.app_pokemon.data.remote


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
// DTO = espelho do JSON de detalhe. Os nomes dos campos precisam ser iguais às chaves do JSON.
// Declaramos só o que o app usa; o resto é ignorado (ignoreUnknownKeys).
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val sprites: SpriteDto,
    val stats: List<StatDto>,
    val types: List<TypeDto>

)

@Serializable
data class SpriteDto(
    // O JSON usa 'front_default' (snake_case); @SerialName liga essa chave ao nome Kotlin 'frontDefault'.
    // 'String?' porque a API pode mandar null; sem o '?', um null quebraria a leitura inteira.
    @SerialName("front_default") val frontDefault: String?
)

@Serializable
data class StatDto(
    // Valor base do atributo (ex.: 45 de HP no Bulbasaur).
    @SerialName("base_stat") val baseStat: Int,
    // O nome do atributo ('hp', 'attack', 'speed'...) vem em um objeto aninhado, por isso a classe StatInfoDto.
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