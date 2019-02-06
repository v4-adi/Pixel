package com.flickr.client.pixel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.flickr.client.util.LogUtil
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Future


/**
 * This class loads image from URL into
 * imageview provided
 * Created by Aditya Mehta on 04/02/19.
 */
object Pixel {

    private val TAG_LAYOUT = 0x40FF0003
    private const val TAG = "Pixel_LOAD_IMAGE"
    private val futureList = mutableListOf<Future<*>>()
    private val imageLruCache = com.flickr.client.di.Injection.imageLruCache

    fun loadImage(url: String, @DrawableRes placeHolder: Int, imageView: ImageView) {
        if (url.isEmpty())
            throw RuntimeException("Url cannot be null or empty")

        imageView.setTag(TAG_LAYOUT, url)
        val cacheBm = getImageFromCache(url)
        if (cacheBm != null) {
            LogUtil.d(TAG, "image found in cache for url: " + url)
            imageView.setImageBitmap(cacheBm)
        } else {
            LogUtil.d(TAG, "image not found in cache for url: " + url)
            imageView.setImageResource(placeHolder)
            downloadImage(url, WeakReference(imageView))
        }

    }

    private fun getImageFromCache(key: String): Bitmap? = imageLruCache.getBitmapFromCache(key)


    private fun downloadImage(url: String, imageView: WeakReference<ImageView>) {
        val future = com.flickr.client.di.Injection
            .appExecutorSupplier.imageLoadingExecutor
            .submit {
                try {
//                    val bmp = getBitmapFromURL(url)
//                    val height = imageView.get()?.height ?: 200
//                    val width = imageView.get()?.width ?: 200
                    val bmp = getBitmapFromUrl(url, 200, 200)
                    setImage(url, bmp, imageView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        futureList.add(future)
    }

    private fun getBitmapFromUrl(imageUrl: String, reqWidth: Int, reqHeight: Int): Bitmap? {

        try {
            val url = URL(imageUrl)
            var connection = url.openConnection() as HttpURLConnection
            var `is` = connection.inputStream


            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`is`, null, options)
            `is`.close()

            connection = url.openConnection() as HttpURLConnection
            `is` = connection.inputStream
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(`is`, null, options)

        } catch (e: Exception) {
            LogUtil.d(TAG, "Exception occured while loading image " + e)
            return null
        }
    }

    fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


    private fun setImage(url: String, bitmap: Bitmap?, imageView: WeakReference<ImageView>) {
        com.flickr.client.di.Injection
            .appExecutorSupplier
            .mainThreadExecutor
            .execute {
                //                val imageView = getImageView(url)
                imageView.get().let { imageView ->
                    if (
                        (imageView?.getTag(TAG_LAYOUT) is String) && imageView.getTag(TAG_LAYOUT) == url &&
                        bitmap != null
                    ) {
                        imageLruCache.addToCache(url, bitmap)
                        imageView.setImageBitmap(bitmap)
                    } else {
                        LogUtil.d(TAG, "bitmap downloaded is null " + url)
                    }
                }
            }
    }

    fun clearTasks() {
        futureList.forEach {
            it.cancel(true)
        }
        futureList.clear()
        imageLruCache.clearCache()
    }
}