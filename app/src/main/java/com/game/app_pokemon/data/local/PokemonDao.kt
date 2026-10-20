package com.game.app_pokemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
// DAO = as operações permitidas na tabela. É só uma interface: o Room gera o código de verdade na compilação (como o Retrofit faz com PokeApiService).
interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    // Traz todas as linhas da tabela. 'suspend' = consulta o disco sem travar a tela.
    suspend fun getAll(): List<PokemonEntity>

    // Grava várias linhas de uma vez. REPLACE: se já existir um id igual, troca a linha em vez de dar erro (rede de proteção).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT COUNT(*) FROM pokemons")
    // Quantas linhas existem; o Repository usa para decidir se já há dados salvos (0 = precisa baixar da internet).
    suspend fun count(): Int
}
