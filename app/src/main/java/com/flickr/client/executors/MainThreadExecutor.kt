package com.flickr.client.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor


/**
 * Created by Aditya Mehta on 04/02/19.
 */
class MainThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())

    override fun execute(task: Runnable?) {
        handler.post(task)
    }
}