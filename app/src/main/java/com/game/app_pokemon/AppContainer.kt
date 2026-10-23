package com.game.app_pokemon

import android.content.Context
import androidx.room.Room
import com.game.app_pokemon.data.local.PokemonDatabase
import com.game.app_pokemon.data.remote.PokeApiClient
import com.game.app_pokemon.data.repository.ImagePreloader
import com.game.app_pokemon.data.repository.PokemonRepository
import com.game.app_pokemon.domain.BattleEngine
import com.game.app_pokemon.domain.DamageCalculator
import com.game.app_pokemon.domain.OpponentGenerator
import kotlin.random.Random

class AppContainer(context: Context) {
    private val database: PokemonDatabase by lazy {
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }
    val battleEngine by lazy { BattleEngine(DamageCalculator(Random.Default), Random.Default) }
    val opponentGenerator by lazy { OpponentGenerator(Random.Default) }
    val pokemonRepository: PokemonRepository by lazy {
        PokemonRepository(
            api = PokeApiClient.api,
            dao = database.pokemonDao(),
            imagePreloader = ImagePreloader(context)
        )
    }
}
