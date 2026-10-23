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

// "Caixa de ferramentas" do app: monta o banco, a API, o Repository e o motor de batalha uma única vez.
// Fica na Application, então vive enquanto o app vive e todas as telas compartilham as mesmas peças
// (um banco só evita conflito de escrita no mesmo arquivo). O Context é necessário para o Room achar onde salvar.
class AppContainer(context: Context) {
    // lazy: só é criado na primeira vez que alguém usa (parecido com um useMemo que roda uma vez).
    // private: as telas só devem conhecer o Repository, nunca o banco direto.
    private val database: PokemonDatabase by lazy {
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }
    // Random.Default é o sorteio de verdade; nos testes seria um Random com semente fixa (ex.: Random(42)).
    val battleEngine by lazy { BattleEngine(DamageCalculator(Random.Default), Random.Default) }
    val opponentGenerator by lazy { OpponentGenerator(Random.Default) }
    // O Repository recebe as peças pelo construtor (injeção de dependência manual).
    val pokemonRepository: PokemonRepository by lazy {
        PokemonRepository(
            api = PokeApiClient.api,
            dao = database.pokemonDao(),
            imagePreloader = ImagePreloader(context)
        )
    }
}
