package com.m68476521.giphierto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.home.TrendingFragmentDirections
import com.m68476521.giphierto.search.SubCategorySelectedFragmentDirections
import kotlinx.android.synthetic.main.image_item.view.*

private const val ITEM = 0
private const val LOADING = 1

class ImagesAdapter() : RecyclerView.Adapter<ImageHolder>() {
    private var imagesList = mutableListOf<Image>()
    private var isLoadingAdded = false

    private lateinit var context: Context

    private var isFromTrending = true

    constructor(isFromTrending: Boolean = true) : this() {
        this.isFromTrending = isFromTrending
    }

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
            val image = imagesList[position].images.original.url
            val imageFixed = imagesList[position].images.fixedHeightDownsampled.url
                ?: return@setOnClickListener
            val title = imagesList[position].title

            val extras = FragmentNavigatorExtras(
                it.imageUrl to image
            )

            val next = if (isFromTrending)
                TrendingFragmentDirections.actionMainHomeFragmentToGiphDialog()
                    .apply {
                        this.image = imageFixed
                        id = imagesList[position].id
                        imageOriginal = image
                        this.title = title

                    }
            else
                SubCategorySelectedFragmentDirections.actionSubCategorySelectedFragmentToGiphDialog()
                    .apply {
                        this.image = imageFixed
                        id = imagesList[position].id
                        imageOriginal = image
                        this.title = title
                    }

            it.findNavController().navigate(next, extras)
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

        itemView.imageUrl.apply {
            transitionName = image
            Glide
                .with(context)
                .asGif()
                .load(image)
                .fitCenter()
                .placeholder(R.drawable.giphy_icon)
                .dontTransform()
                .into(itemView.imageUrl)
        }
    }
}
