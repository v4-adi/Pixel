package com.flickr.client.search.image.data.model

data class ImageSearchResult(
    val photos: Photos?,
    val stat: String?,
    val code: Int?,
    val message:String?
)