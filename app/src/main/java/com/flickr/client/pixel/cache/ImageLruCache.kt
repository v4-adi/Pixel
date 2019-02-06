package com.flickr.client.pixel.cache

import android.graphics.Bitmap

/**
 * Created by Aditya Mehta on 05/02/19.
 * This class is wrapper for Image LRU cache
 */
class ImageLruCache(availableMaxMem:Long) {

    private val imageMemoryLruCache = ImageMemoryLruCache(availableMaxMem)

    fun addToCache(key: String, bitmap: Bitmap) {
        imageMemoryLruCache.addToMemCache(key, bitmap)
    }

    fun getBitmapFromCache(key: String): Bitmap? = imageMemoryLruCache.getBitmap(key)

    fun clearCache() = imageMemoryLruCache.clearMemoryCache()
}