package com.flickr.client

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ALPHA_8
import com.flickr.client.pixel.cache.ImageLruCache
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ImageLruCacheTest {

    @Test
    fun checkImageLRuCache() {
        val imageCache = ImageLruCache(1024 * 1024)
        var count = 0
        var idx = 1
        imageCache.addToCache("first", Bitmap.createBitmap(200, 200, ALPHA_8))

        assert(imageCache.getBitmapFromCache("first") != null)
        imageCache.addToCache("second", Bitmap.createBitmap(200, 200, ALPHA_8))

        assert(imageCache.getBitmapFromCache("first") != null)
        assert(imageCache.getBitmapFromCache("second") != null)

        imageCache.addToCache("third", Bitmap.createBitmap(520, 520, ALPHA_8))

        assert(imageCache.getBitmapFromCache("third") == null)

        imageCache.addToCache("second", Bitmap.createBitmap(200, 200, ALPHA_8))

        assert(imageCache.getBitmapFromCache("second") != null)
    }
}