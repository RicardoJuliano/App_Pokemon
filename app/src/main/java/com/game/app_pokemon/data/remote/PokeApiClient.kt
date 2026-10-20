package com.game.app_pokemon.data.remote


import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// 'object' = instância única (singleton): o app inteiro compartilha o mesmo cliente de rede,
// em vez de criar um Retrofit novo a cada chamada.
object PokeApiClient {

    // Leitor de JSON. 'ignoreUnknownKeys' ignora campos que a API manda e que os nossos DTOs não declaram
    // (a PokeAPI devolve muito mais coisa do que usamos).
    private val json = Json { ignoreUnknownKeys = true }

    // Monta o Retrofit e gera sozinho a implementação da interface PokeApiService.
    val api: PokeApiService = Retrofit.Builder()
        // Endereço-base da API: precisa ter 'https://' e terminar com '/'. Os @GET da interface são somados a ele.
        .baseUrl("https://pokeapi.co/api/v2/")
        // Converte o JSON recebido em objetos Kotlin (DTOs) usando o kotlinx.serialization.
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(PokeApiService::class.java)
}