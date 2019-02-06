package com.flickr.client.di

import com.flickr.client.network.NetworkRequest
import com.flickr.client.pixel.cache.ImageLruCache
import com.flickr.client.search.image.data.ImageSearchRepository
import com.flickr.client.util.getMaxMemory

object Injection {
    val appExecutorSupplier: com.flickr.client.executors.AppExecutorSupplier =
        com.flickr.client.executors.AppExecutorSupplier()
    val networkRequest: NetworkRequest = NetworkRequest()
    val imageLruCache = ImageLruCache(getMaxMemory())

    fun provideImageSearchRepository(): ImageSearchRepository {
        return ImageSearchRepository(
            com.flickr.client.di.Injection.appExecutorSupplier,
            com.flickr.client.di.Injection.networkRequest
        )
    }
}