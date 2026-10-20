package com.game.app_pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// O banco: lista as tabelas (entities) e a versão do esquema (muda quando a estrutura das tabelas mudar).
@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    // Entrega o DAO; o Room escreve a implementação. O banco em si é criado com Room.databaseBuilder (precisa de Context).
    abstract fun pokemonDao(): PokemonDao
}