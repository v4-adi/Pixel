package com.flickr.client.search.image.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flickr.client.pixel.Pixel
import com.flickr.client.search.image.data.model.Photo
import com.flickr.client.R
import kotlinx.android.synthetic.main.item_photo.view.*

class ImageSearchAdapter(
    private val photos: List<Photo>,
    private val lastItemInflated: () -> Unit
) :
    RecyclerView.Adapter<ImageSearchAdapter.FlickrImageSearchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageSearchViewHolder =
        FlickrImageSearchViewHolder
            .create(parent)


    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: FlickrImageSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (itemCount >= 10 && position == itemCount - 5) lastItemInflated.invoke()
        else if (itemCount == position) lastItemInflated.invoke()
    }


    private fun getItem(position: Int) = photos[position]

    class FlickrImageSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(photo: Photo) {
            itemView.imgTitleTv.text = photo.title
            Pixel.loadImage(photo.photoUrl, R.drawable.ic_baseline_photo_24px, itemView.searchResultImg)
        }

        companion object {
            fun create(parent: ViewGroup): FlickrImageSearchViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)
                return FlickrImageSearchViewHolder(view)
            }
        }
    }
}