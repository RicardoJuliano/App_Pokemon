package com.game.app_pokemon.data.repository


import com.game.app_pokemon.data.local.PokemonDao
import com.game.app_pokemon.data.remote.PokeApiService
import com.game.app_pokemon.domain.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.coroutineScope


class PokemonRepository(
    private val api: PokeApiService,
    private val dao: PokemonDao,
    private val imagePreloader: ImagePreloader
) {
    suspend fun getPokemons(): List<Pokemon> {
        if (dao.count() == 0) {
            val lista = api.getlistPokemons(limit = 151)

            val semaphore = Semaphore(10)

            val entities = coroutineScope{
                lista.results.map { item ->
                    async {
                        semaphore.withPermit {
                            val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                            api.getPokemonDetail(id).toEntity()
                        }
                    }
            }.awaitAll()
            }
            imagePreloader.preload(
                entities.map{it.toDomain().imageUrl}
            )
            dao.insertAll(entities)
        }
        return dao.getAll().map { it.toDomain() }
    }
}