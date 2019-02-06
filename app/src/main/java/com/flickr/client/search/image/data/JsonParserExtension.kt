package com.flickr.client.search.image.data

import com.flickr.client.search.image.data.model.ImageSearchResult
import com.flickr.client.search.image.data.model.Photo
import com.flickr.client.search.image.data.model.Photos
import org.json.JSONObject

/**
 * Created by Aditya Mehta on 04/02/19.
 */

fun String.parseImageSearchResult(): ImageSearchResult {
    val jsonObject = JSONObject(this)
    return ImageSearchResult(
        jsonObject.getPhotos()
        , jsonObject.getString("stat")
        , jsonObject.optInt("code")
        , jsonObject.optString("message")
    )
}

private fun JSONObject.getPhotos(): Photos {
    val photosJson = this.optJSONObject("photos")
    return Photos(
        photosJson.optInt("page")
        , photosJson.optInt("pages")
        , photosJson.optInt("perpage")
        , photosJson.getPhotoList()
        , photosJson.optString("total")

    )
}

private fun JSONObject.getPhotoList(): List<Photo> {
    val photoJsonArr = this.getJSONArray("photo")
    val photoList = mutableListOf<Photo>()
    for (i in 0 until photoJsonArr.length()) {
        val photo = photoJsonArr.getJSONObject(i)
        photoList.add(
            Photo(
                photo.optInt("farm"),
                photo.optString("id"),
                photo.optInt("isfamily"),
                photo.optInt("isfriend"),
                photo.optInt("ispublic"),
                photo.optString("owner"),
                photo.optString("secret"),
                photo.optString("server"),
                photo.optString("title")
            )
        )
    }
    return photoList
}