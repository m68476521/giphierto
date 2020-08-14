package com.m68476521.giphierto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.home.TrendingFragmentDirections
import kotlinx.android.synthetic.main.image_item.view.*

private const val ITEM = 0
private const val LOADING = 1

class ImagesAdapter : RecyclerView.Adapter<ImageHolder>() {
    private var imagesList = mutableListOf<Image>()
    private var isLoadingAdded = false

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        context = parent.context
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = imagesList[position].images.fixedHeightDownsampled.url ?: return
        holder.bind(
            image,
            context
        )

        holder.itemView.setOnClickListener {
            val image = imagesList[position].images.fixedWidthDownsampled.url ?: return@setOnClickListener
            val next = TrendingFragmentDirections.actionTrendingFragmentToGiphDialog()
            next.image = image
            it.findNavController().navigate(next)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == imagesList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addAll(newImageList: List<Image>) {
        imagesList.addAll(newImageList)
        notifyItemInserted(imagesList.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = imagesList.size - 1
        imagesList.removeAt(position)
        notifyItemRemoved(position)
    }
}

class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(image: String, context: Context) {
        Glide
            .with(context)
            .asGif()
            .load(image)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_background)
            .dontTransform()
            .into(itemView.imageUrl)
    }
}
