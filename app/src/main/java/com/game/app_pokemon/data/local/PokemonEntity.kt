package com.game.app_pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val hp: Int,
    val attack : Int,
    val defense : Int,
    val specialAttack : Int,
    val specialDefense: Int,
    val speed: Int,
    val types: String
)
