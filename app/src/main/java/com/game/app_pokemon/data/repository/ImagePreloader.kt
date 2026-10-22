package com.game.app_pokemon.data.repository

import android.content.Context
import coil3.SingletonImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class ImagePreloader(
    private val context: Context
) {
    suspend fun preload(urls: List<String>)
    {
        val imageLoader = SingletonImageLoader.get(context)
        val semaphore = Semaphore(10)

        coroutineScope{
            urls.forEach { url ->
                launch {
                    semaphore.withPermit {
                        val request = ImageRequest.Builder(context)
                            .data(url)
                            .build()

                        val result = imageLoader.execute(request)

                        if (result is ErrorResult) throw result.throwable
                    }
                }
            }
        }
    }
}