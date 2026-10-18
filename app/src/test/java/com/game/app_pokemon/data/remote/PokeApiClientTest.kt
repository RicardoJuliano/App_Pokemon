package com.game.app_pokemon.data.remote


import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PokeApiClientTest {

    @Test
    fun getListPokemons_returnTheQuantityExpected() = runBlocking {
        val response = PokeApiClient.api.getlistPokemons(151)

        assertEquals(151, response.results.size)
        assertTrue(response.results.first().name.isNotBlank())
    }
    @Test
    fun getListPokemons_returnTheNamePokemonExpected() = runBlocking {
        val response = PokeApiClient.api.getlistPokemons(151)

        assertEquals("bulbasaur", response.results.first().name)

    }
    @Test
    fun getPokemonDetail_returnTheDetailPokemonExpected() = runBlocking {
        val response = PokeApiClient.api.getPokemonDetail(1)

        assertEquals("bulbasaur", response.name)
        assertEquals(6, response.stats.size)
        assertNotNull(response.sprites.frontDefault)
        assertEquals(45, response.stats.first{it.stat.name == "hp"}.baseStat)

    }
}