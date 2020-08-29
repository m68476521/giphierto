package com.m68476521.giphierto.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.m68476521.giphierto.ImageHolder
import com.m68476521.giphierto.R
import com.m68476521.giphierto.data.Image

class FavoriteAdapter : RecyclerView.Adapter<ImageHolder>() {
    private var imagesList = mutableListOf<Image>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        context = parent.context
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun getItemCount() = imagesList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = imagesList[position].fixedHeightDownsampled
        holder.bind(
            image,
            context
        )
    }

    fun addAll(newImageList: List<Image>) {
        imagesList.addAll(newImageList)
        notifyItemInserted(imagesList.size - 1)
    }
}