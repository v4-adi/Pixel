package com.flickr.client.search.image

import com.flickr.client.pixel.Pixel
import com.flickr.client.search.image.data.ImageSearchRepository
import com.flickr.client.search.image.data.model.ImageSearchResult
import com.flickr.client.util.PER_PAGE_RESULT_SIZE

class ImageSearchPresenter(
    val imageSearchRepository: ImageSearchRepository,
    var imageSearchview: ImageSearchResultContract.View?
) : ImageSearchResultContract.Presenter {

    private var searchText: String? = null
    private var isLoading: Boolean? = false
    private var totalPage: Int = 0
    private var currentPageNo: Int = 0

    init {
        imageSearchview?.presenter = this
    }

    override fun searchImages(searchText: String) {
        searchImages(searchText, false)
    }

    override fun searchImages(searchText: String, loadMore: Boolean) {
        if (this.searchText != searchText) {
            this.searchText = searchText
            Pixel.clearTasks()
        }

        this.isLoading = true
        if (!loadMore) {
            imageSearchview?.clearImages()
            imageSearchview?.showProgressBar()
        } else {
            imageSearchview?.showFooterProgressBar()
        }
        imageSearchview?.hideErrorView()

        imageSearchRepository.searchImages(searchText, currentPageNo + 1, PER_PAGE_RESULT_SIZE,
            object : com.flickr.client.BaseDataSource.ApiCallback<ImageSearchResult> {
                override fun onDataAvailable(data: ImageSearchResult) {
                    if (!data.photos?.photo.isNullOrEmpty()) {
                        currentPageNo = data.photos?.page ?: 0
                        totalPage = data.photos?.pages ?: 0
                        data.photos?.photo?.let { imageSearchview?.addImages(it) }
                    }
                    imageSearchview?.hideProgressBar()
                    imageSearchview?.hideFooterProgressBar()
                    isLoading = false
                }

                override fun onError(error: com.flickr.client.Error) {
                    imageSearchview?.hideProgressBar()
                    imageSearchview?.hideFooterProgressBar()
                    isLoading = false
                    imageSearchview?.showErrorView(error)
                }
            }
        )
    }

    override fun loadMoreItems() {
        if (isLoading == false && currentPageNo < totalPage) {
            searchText?.let { searchImages(it, true) }
        }
    }

    override fun destroy() {
        imageSearchview = null
    }
}