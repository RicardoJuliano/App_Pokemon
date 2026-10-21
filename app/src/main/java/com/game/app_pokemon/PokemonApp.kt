package com.game.app_pokemon

import android.app.Application

// Application: é criada uma vez, antes de qualquer tela, quando o processo do app começa.
// Precisa estar registrada no AndroidManifest (android:name) para o Android usá-la.
class PokemonApp: Application() {
    // lateinit: ainda não tem valor aqui, mas é preenchido no onCreate antes de qualquer uso.
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // `this` é um Context: a própria Application.
        container = AppContainer(this)
    }
}