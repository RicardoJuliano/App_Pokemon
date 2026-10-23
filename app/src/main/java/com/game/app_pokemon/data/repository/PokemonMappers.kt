package com.game.app_pokemon.data.repository

import com.game.app_pokemon.data.local.PokemonEntity
import com.game.app_pokemon.data.remote.PokemonDetailDto
import com.game.app_pokemon.domain.Pokemon

fun PokemonDetailDto.toEntity(): PokemonEntity {

    fun stat(name: String) = stats.first { it.stat.name == name }.baseStat

    return PokemonEntity(
        id = id,
        name = name,
        hp = stat("hp"),
        attack = stat("attack"),
        defense = stat("defense"),
        specialAttack = stat("special-attack"),
        specialDefense = stat("special-defense"),
        speed = stat("speed"),
        types = types.joinToString(",") { it.type.name }
    )
}


fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed
    )
}

