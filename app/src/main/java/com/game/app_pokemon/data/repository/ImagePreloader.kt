package com.game.app_pokemon.data.repository

import android.content.Context
import coil3.SingletonImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest

// Baixa as imagens antes de serem exibidas. O Coil só guardava no cache o que já tinha aparecido na tela;
// pré-carregando as 151, o modo avião mostra todas as fotos.
class ImagePreloader(
    private val context: Context
) {
    // suspend: espera cada download terminar sem travar a tela.
    suspend fun preload(urls: List<String>)
    {
        // O mesmo carregador que o AsyncImage usa: assim as imagens vão para o cache que a lista consulta.
        val imageLoader = SingletonImageLoader.get(context)

        // Uma imagem por vez (a concorrência limitada vem depois).
        urls.forEach { url ->
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