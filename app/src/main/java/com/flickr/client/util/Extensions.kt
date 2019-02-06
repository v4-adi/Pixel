package com.flickr.client.util

import com.flickr.client.network.FLICKR_API_KEY

fun String.buildQueryMap(page: Int = 0, perPage: Int = 100): Map<String, String> {
    val queryMap = HashMap<String, String>()
    queryMap.put("api_key", FLICKR_API_KEY)
    queryMap.put("format", "json")
    queryMap.put("nojsoncallback", "1")
    queryMap.put("safe_search", "1")
    queryMap.put("text", this)
    queryMap.put("per_page", perPage.toString())
    queryMap.put("page", page.toString())
    return queryMap
}

fun Map<String, String>.getQueryParam(): String {
    val querySb = StringBuilder()
    forEach {
        querySb.append("&")
        querySb.append(it.key)
        querySb.append("=")
        querySb.append(it.value)
    }
    return querySb.toString()
}