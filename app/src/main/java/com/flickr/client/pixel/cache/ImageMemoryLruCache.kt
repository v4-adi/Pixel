package com.flickr.client.pixel.cache

import android.graphics.Bitmap
import androidx.collection.LruCache

/**
 * Created by Aditya Mehta on 05/02/19.
 * This class maintain in memory lru of bitmap
 */
class ImageMemoryLruCache(maxAvailableMem:Long) {

    private var memoryCache: LruCache<String, Bitmap>

    init {
        val maxMemory = (maxAvailableMem/ 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun addToMemCache(key: String, bitmap: Bitmap) = memoryCache.put(key, bitmap)

    fun getBitmap(key: String) = memoryCache[key]

    fun clearMemoryCache() = memoryCache.evictAll()
}