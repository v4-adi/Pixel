package com.flickr.client.search.image

import com.flickr.client.search.image.data.model.Photo

/**
 * This specifies the contract between the view and the presenter.
 */
interface ImageSearchResultContract {

    interface View : com.flickr.client.BaseView<Presenter> {
        fun addImages(photoList: List<Photo>)
        fun clearImages()
        fun showProgressBar()
        fun hideProgressBar()
        fun showFooterProgressBar()
        fun hideFooterProgressBar()
        fun showErrorView(error: com.flickr.client.Error)
        fun hideErrorView()
    }

    interface Presenter : com.flickr.client.BasePresenter {
        fun searchImages(searchText: String, loadMore: Boolean)
        fun searchImages(searchText: String)
        fun loadMoreItems()
    }
}