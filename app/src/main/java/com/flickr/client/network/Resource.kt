package com.flickr.client.network

data class Resource<T> (
    var data:T? =null,
    var error: com.flickr.client.Error? =null
)