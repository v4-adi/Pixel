package com.flickr.client

import android.os.Bundle
import com.flickr.client.di.Injection
import com.flickr.client.search.image.ImageSearchPresenter
import com.flickr.client.search.image.fragments.ImageSearchResultFragment
import com.flickr.client.util.replaceFragmentInActivity

class MainActivity : com.flickr.client.BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.flickr.client.R.layout.activity_main)
        init()
    }

    private fun init() {
        val fragment = supportFragmentManager
            .findFragmentById(com.flickr.client.R.id.searchFrame) as ImageSearchResultFragment?
            ?: ImageSearchResultFragment.newInstance().also {
                replaceFragmentInActivity(it, com.flickr.client.R.id.searchFrame)
            }
        ImageSearchPresenter(Injection.provideImageSearchRepository(), fragment)
    }
}
