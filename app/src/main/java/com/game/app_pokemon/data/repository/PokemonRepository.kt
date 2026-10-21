package com.game.app_pokemon.data.repository


import com.game.app_pokemon.data.local.PokemonDao
import com.game.app_pokemon.data.remote.PokeApiService
import com.game.app_pokemon.domain.Pokemon

// Repository: o ViewModel pede "me dá os Pokémon" e não sabe se vieram do banco ou da internet.
// api e dao entram pelo construtor (injeção de dependência): assim dá para trocá-los por falsos em testes.
class PokemonRepository(
    private val api: PokeApiService,
    private val dao: PokemonDao
) {
    // suspend: faz rede e banco, então não pode rodar na thread principal.
    suspend fun getPokemons(): List<Pokemon> {
        // Banco vazio = primeira abertura do app: baixa tudo uma vez. Nas próximas, pula este bloco.
        if (dao.count() == 0) {
            // 1 chamada: lista só com nome e url dos 151 primeiros.
            val lista = api.getlistPokemons(limit = 151)

            val entities = lista.results.map { item ->

                // O id está no fim da url (".../pokemon/25/" -> 25).
                val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                // 1 chamada por Pokémon (os atributos de batalha só vêm no detalhe); DTO -> Entity.
                api.getPokemonDetail(id).toEntity()
            }
            // Só salva depois de baixar tudo: se a internet cair no meio, nada fica pela metade.
            dao.insertAll(entities)
        }
        // Sempre devolve do banco (fonte única da verdade): primeira vez e offline mostram o mesmo.
        return dao.getAll().map { it.toDomain() }
    }
}