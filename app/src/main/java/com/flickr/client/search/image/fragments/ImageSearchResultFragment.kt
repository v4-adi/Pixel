package com.flickr.client.search.image.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.flickr.client.search.image.ImageSearchResultContract
import com.flickr.client.search.image.adapter.ImageSearchAdapter
import com.flickr.client.search.image.data.model.Photo
import com.google.android.material.snackbar.Snackbar
import com.flickr.client.R
import kotlinx.android.synthetic.main.fragment_flickr_image_search_result.*


class ImageSearchResultFragment : Fragment(), ImageSearchResultContract.View, SearchView.OnQueryTextListener {
    override lateinit var presenter: ImageSearchResultContract.Presenter

    private var errorSnackBar: Snackbar? = null
    private val searchResults: MutableList<Photo> by lazy {
        mutableListOf<Photo>()
    }
    private val searchResultAdapter: ImageSearchAdapter by lazy {
        ImageSearchAdapter(searchResults) {
            presenter.loadMoreItems()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flickr_image_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setSearchView()
        initRv()
        loadDefaultData()
    }

    private fun loadDefaultData() {
        presenter.searchImages("Cars")
    }

    private fun setSearchView() {

        searchToolbar.inflateMenu(R.menu.menu_search_fragment)

        val searchItem = searchToolbar.menu.findItem(R.id.action_search)
        val searchActionView = searchItem.actionView as SearchView
        searchActionView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            presenter.searchImages(it)
            searchToolbar.menu.findItem(R.id.action_search)
                .actionView.clearFocus()
            searchToolbar.title = query
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun initRv() {
        searchResultRv.layoutManager = GridLayoutManager(context, 3)
        searchResultRv.adapter = searchResultAdapter
    }

    override fun addImages(photoList: List<Photo>) {
        searchResults.addAll(photoList)
        searchResultAdapter.notifyDataSetChanged()
    }

    override fun clearImages() {
        searchResults.clear()
        searchResultAdapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        searchLoadingProgresBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        searchLoadingProgresBar.visibility = View.GONE
    }

    override fun showErrorView(error: com.flickr.client.Error) {
        errorSnackBar =
            Snackbar.make(searchResultParent, getString(R.string.something_went_wrong), Snackbar.LENGTH_INDEFINITE)
                .apply {
                    show()
                }
    }

    override fun hideErrorView() {
        errorSnackBar?.dismiss()
    }

    override fun showFooterProgressBar() {
        footerLoadingProgBar.visibility = View.VISIBLE
    }

    override fun hideFooterProgressBar() {
        footerLoadingProgBar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ImageSearchResultFragment()
    }
}
