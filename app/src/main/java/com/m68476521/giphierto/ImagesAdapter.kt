package com.m68476521.giphierto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.m68476521.giphierto.api.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_item.view.*

class ImagesAdapter : RecyclerView.Adapter<ImageHolder>() {

    private var imagesList: List<Image> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(imagesList[position].images.fixedHeightSmall.url, imagesList[position].title)
    }

    fun swapImages(images: List<Image>) {
        if (this.imagesList === images)
            return

        this.imagesList = images
        this.notifyDataSetChanged()
    }
}

class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(image: String, name: String) {
        Picasso.get()
            .load(image)
            .into(itemView.imageUrl)
        itemView.title.text = name
    }
}