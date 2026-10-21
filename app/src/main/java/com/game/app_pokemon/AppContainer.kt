package com.game.app_pokemon

import android.content.Context
import androidx.room.Room
import com.game.app_pokemon.data.local.PokemonDatabase
import com.game.app_pokemon.data.remote.PokeApiClient
import com.game.app_pokemon.data.repository.PokemonRepository

class AppContainer(context: Context) {
    private val database: PokemonDatabase by lazy {
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "cardapio.db"
        ).build()
    }
    val pokemonRepository: PokemonRepository by lazy {
        PokemonRepository(
            api = PokeApiClient.api,
            dao = database.pokemonDao()
        )
    }
}
