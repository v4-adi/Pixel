package com.flickr.client

interface BaseDataSource {

    interface ApiCallback<T> {

        fun onDataAvailable(data:T)

        fun onError(error: com.flickr.client.Error)
    }
}