package com.flickr.client.search.image.data.model

data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val photoUrl: String = "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"
)