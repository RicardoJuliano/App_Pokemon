package com.game.app_pokemon.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Contrato das chamadas à PokeAPI. Só descrevemos as chamadas; o Retrofit escreve o código de rede.
interface PokeApiService {

    // Lista de Pokémon: GET .../pokemon?limit=151 (cada item traz só 'name' e 'url').
    @GET("pokemon")
    // 'suspend' = espera a rede sem travar a tela; só pode ser chamada de dentro de uma corrotina.
    // @Query("limit") vira o '?limit=...' no endereço.
    suspend fun getlistPokemons(@Query("limit") limit: Int): PokemonListDto

    // Detalhe de um Pokémon: GET .../pokemon/1 (traz stats, tipos e sprites).
    @GET("pokemon/{id}")
    // @Path("id") preenche o {id} do endereço com o valor do parâmetro.
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailDto
}