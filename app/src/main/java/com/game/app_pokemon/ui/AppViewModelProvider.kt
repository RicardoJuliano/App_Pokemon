package com.game.app_pokemon.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.game.app_pokemon.PokemonApp
import com.game.app_pokemon.ui.list.ListViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListViewModel(pokemonApplication().container.pokemonRepository)
        }
    }


}


fun CreationExtras.pokemonApplication(): PokemonApp =

    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokemonApp