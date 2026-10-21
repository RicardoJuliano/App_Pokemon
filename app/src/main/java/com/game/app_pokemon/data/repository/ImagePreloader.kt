package com.game.app_pokemon.data.repository

import android.content.Context
import coil3.SingletonImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest

class ImagePreloader(
    private val context: Context
) {
    suspend fun preload(urls: List<String>)
    {
        val imageLoader = SingletonImageLoader.get(context)

        urls.forEach { url ->
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()

            val result = imageLoader.execute(request)

            if (result is ErrorResult) throw result.throwable
        }
    }
}