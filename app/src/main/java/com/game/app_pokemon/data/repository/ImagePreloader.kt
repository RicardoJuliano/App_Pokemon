package com.game.app_pokemon.data.repository

import android.content.Context
import coil3.SingletonImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

// Baixa as imagens antes de serem exibidas. O Coil só guardava no cache o que já tinha aparecido na tela;
// pré-carregando as 151, o modo avião mostra todas as fotos.
class ImagePreloader(
    private val context: Context
) {
    // suspend: espera os downloads terminarem sem travar a tela.
    suspend fun preload(urls: List<String>)
    {
        // O mesmo carregador que o AsyncImage usa: assim as imagens vão para o cache que a lista consulta.
        val imageLoader = SingletonImageLoader.get(context)
        // No máximo 10 downloads ao mesmo tempo (janela deslizante), para não sobrecarregar a rede nem o servidor.
        val semaphore = Semaphore(10)

        // coroutineScope só termina quando TODAS as tarefas terminam; se uma falhar, cancela as outras e relança o erro.
        coroutineScope{
            urls.forEach { url ->
                // launch: uma tarefa por imagem. Não usamos async porque não precisamos de um valor de volta.
                launch {
                    // Espera uma vaga livre (máx. 10 ao mesmo tempo).
                    semaphore.withPermit {
                        val request = ImageRequest.Builder(context)
                            .data(url)
                            .build()

                        // execute baixa e guarda no cache; não lança erro sozinho, devolve um resultado.
                        val result = imageLoader.execute(request)

                        // Transforma falha silenciosa em exceção, para o Repository não salvar o banco sem as imagens.
                        if (result is ErrorResult) throw result.throwable
                    }
                }
            }
        }
    }
}