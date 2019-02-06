package com.flickr.client.network

import com.flickr.client.util.getQueryParam
import java.net.URL


class NetworkRequest {

    /**
     * Returns entire content of this URL as String
     */
    fun get(url: String?, queryParams: Map<String, String>?): Resource<String> {
        try {
            val urlResult = URL(
                BASE_URL + url +
                        queryParams?.getQueryParam()
            ).readText()
            return Resource(urlResult, com.flickr.client.Error())
        } catch (ex: Exception) {
            return Resource(null, com.flickr.client.Error(-1, "Something Went Wrong", ex))
        }
    }
}