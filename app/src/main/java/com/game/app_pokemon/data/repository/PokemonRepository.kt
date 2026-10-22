package com.game.app_pokemon.data.repository


import com.game.app_pokemon.data.local.PokemonDao
import com.game.app_pokemon.data.remote.PokeApiService
import com.game.app_pokemon.domain.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.coroutineScope


// Repository: o ViewModel pede "me dá os Pokémon" e não sabe se vieram do banco ou da internet.
// api, dao e imagePreloader entram pelo construtor (injeção de dependência): assim dá para trocá-los por falsos em testes.
class PokemonRepository(
    private val api: PokeApiService,
    private val dao: PokemonDao,
    private val imagePreloader: ImagePreloader
) {
    // suspend: faz rede e banco, então não pode rodar na thread principal.
    suspend fun getPokemons(): List<Pokemon> {
        // Banco vazio = primeira abertura do app: baixa tudo uma vez. Nas próximas, pula este bloco.
        if (dao.count() == 0) {
            // 1 chamada: lista só com nome e url dos 151 primeiros.
            val lista = api.getlistPokemons(limit = 151)

            // Semaphore: no máximo 10 downloads ao mesmo tempo (janela deslizante: quando um termina, entra o próximo).
            // Evita sobrecarregar a API e o aparelho, e deixa o limite explícito no nosso código.
            val semaphore = Semaphore(10)

            // coroutineScope: se uma tarefa falhar, cancela as outras e relança o erro (tudo-ou-nada).
            val entities = coroutineScope{
                // Uma tarefa (async) por Pokémon; todas são disparadas, mas o Semaphore deixa só 10 trabalharem por vez.
                lista.results.map { item ->
                    async {
                        semaphore.withPermit {
                            // O id está no fim da url (".../pokemon/25/" -> 25).
                            val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                            // 1 chamada por Pokémon (os atributos de batalha só vêm no detalhe); DTO -> Entity.
                            api.getPokemonDetail(id).toEntity()
                        }
                    }
            // awaitAll: espera todas terminarem e devolve os resultados na mesma ordem da lista.
            }.awaitAll()
            }
            // Baixa as 151 imagens para o cache do Coil, para a lista funcionar offline com as fotos.
            // Vem ANTES do insertAll: se a internet cair no meio, nada é salvo e o app tenta tudo de novo na próxima abertura.
            imagePreloader.preload(
                entities.map{it.toDomain().imageUrl}
            )
            // Só salva depois de baixar tudo (dados e imagens): nada fica pela metade.
            dao.insertAll(entities)
        }
        // Sempre devolve do banco (fonte única da verdade): primeira vez e offline mostram o mesmo.
        return dao.getAll().map { it.toDomain() }
    }
}