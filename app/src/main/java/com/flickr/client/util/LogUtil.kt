package com.flickr.client.util

import android.util.Log
import com.flickr.client.BuildConfig

/**
 * Created by Aditya Mehta on 05/02/19.
 */

object LogUtil {
    private  var isDebuggable = BuildConfig.DEBUG

    fun d(tag:String,msg:String) {
        if (isDebuggable)
            Log.d(tag,msg)
    }
}