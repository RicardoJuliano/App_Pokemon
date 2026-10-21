package com.game.app_pokemon.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.game.app_pokemon.PokemonApp
import com.game.app_pokemon.ui.list.ListViewModel


// Receita (Factory) que ensina o Android a criar ViewModels que precisam de parâmetros.
// O Android só sabe criar ViewModels com construtor vazio; aqui dizemos de onde tirar o Repository.
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            // Pega o Repository do container da Application: é o mesmo para todas as telas.
            ListViewModel(pokemonApplication().container.pokemonRepository)
        }
    }


}


// Atalho: a partir dos extras da criação do ViewModel, chega na nossa Application (e no container dela).
fun CreationExtras.pokemonApplication(): PokemonApp =

    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokemonApp