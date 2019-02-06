package com.flickr.client.executors

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Aditya Mehta on 04/02/19.
 */
class AppExecutorSupplier {

    val mainThreadExecutor: Executor
    val networkExecutor: ExecutorService
    val imageLoadingExecutor: ExecutorService

    init {
        mainThreadExecutor = com.flickr.client.executors.MainThreadExecutor()
        networkExecutor = Executors.newSingleThreadExecutor()
        imageLoadingExecutor = Executors.newFixedThreadPool(getBestThreadCount())
    }

    private fun getBestThreadCount() =
        Math.min(com.flickr.client.executors.AppExecutorSupplier.MAXIMUM_AUTOMATIC_THREAD_COUNT, Runtime.getRuntime().availableProcessors())
    companion object {
        private const val MAXIMUM_AUTOMATIC_THREAD_COUNT = 4
    }
}