package com.game.app_pokemon.data.repository


import com.game.app_pokemon.data.local.PokemonDao
import com.game.app_pokemon.data.remote.PokeApiService
import com.game.app_pokemon.domain.Pokemon


class PokemonRepository(
    private val api: PokeApiService,
    private val dao: PokemonDao,
    private val imagePreloader: ImagePreloader
) {
    suspend fun getPokemons(): List<Pokemon> {
        if (dao.count() == 0) {
            val lista = api.getlistPokemons(limit = 151)

            val entities = lista.results.map { item ->

                val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                api.getPokemonDetail(id).toEntity()
            }
            imagePreloader.preload(
                entities.map{it.toDomain().imageUrl}
            )
            dao.insertAll(entities)
        }
        return dao.getAll().map { it.toDomain() }
    }
}