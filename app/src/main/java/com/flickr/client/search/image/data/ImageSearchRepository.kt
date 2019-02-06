package com.flickr.client.search.image.data

import com.flickr.client.network.APPEND_SEARCH_URL
import com.flickr.client.network.NetworkRequest
import com.flickr.client.search.image.data.model.ImageSearchResult
import com.flickr.client.util.buildQueryMap


class ImageSearchRepository(val appExecutorSupplier: com.flickr.client.executors.AppExecutorSupplier, val networkRequest: NetworkRequest) {


    fun searchImages(
        searchText: String, page: Int, perPage: Int,
        searchResultCallback: com.flickr.client.BaseDataSource.ApiCallback<ImageSearchResult>
    ) {
        appExecutorSupplier.networkExecutor
            .submit {
                try {
                    val result = networkRequest
                        .get(APPEND_SEARCH_URL, searchText.buildQueryMap(page, perPage))
                    if (result.data != null) {
                        val imageSearchResultData = result.data
                        val imageSearchResult = imageSearchResultData?.parseImageSearchResult()

                        if (imageSearchResult?.stat == "ok") {
                            dispatchSuccess(imageSearchResult, searchResultCallback)
                        } else {
                            dispatchError(
                                com.flickr.client.Error(
                                    imageSearchResult?.code, imageSearchResult?.message, null
                                ), searchResultCallback
                            )
                        }
                    }
                } catch (exe: Exception) {
                    dispatchError(
                        com.flickr.client.Error(
                            -1, "Something Went Wrong", exe
                        ), searchResultCallback
                    )
                }
            }
    }

    private fun dispatchSuccess(
        imageSearchResult: ImageSearchResult,
        searchResultCallback: com.flickr.client.BaseDataSource.ApiCallback<ImageSearchResult>
    ) {
        appExecutorSupplier.mainThreadExecutor
            .execute { searchResultCallback.onDataAvailable(imageSearchResult) }
    }

    private fun dispatchError(error: com.flickr.client.Error, searchResultCallback: com.flickr.client.BaseDataSource.ApiCallback<ImageSearchResult>) {
        appExecutorSupplier.mainThreadExecutor
            .execute { searchResultCallback.onError(error) }
    }
}