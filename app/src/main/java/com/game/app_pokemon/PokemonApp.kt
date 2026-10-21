package com.game.app_pokemon

import android.app.Application

class PokemonApp: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}