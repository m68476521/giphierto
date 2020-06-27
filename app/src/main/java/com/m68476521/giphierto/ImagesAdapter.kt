package com.m68476521.giphierto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.api.Image
import kotlinx.android.synthetic.main.image_item.view.*

class ImagesAdapter : RecyclerView.Adapter<ImageHolder>() {

    private var imagesList: List<Image> = emptyList()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        context = parent.context
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(
            imagesList[position].images.fixedHeightSmall.url,
            imagesList[position].title,
            context
        )
    }

    fun swapImages(images: List<Image>) {
        if (this.imagesList === images)
            return

        this.imagesList = images
        this.notifyDataSetChanged()
    }
}

class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(image: String, name: String, context: Context) {
        itemView.title.text = name

        Glide
            .with(context)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(itemView.imageUrl)
    }
}
