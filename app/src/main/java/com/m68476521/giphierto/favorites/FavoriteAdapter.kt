package com.m68476521.giphierto.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.m68476521.giphierto.ImageHolder
import com.m68476521.giphierto.R
import com.m68476521.giphierto.data.Image
import kotlinx.android.synthetic.main.image_item.view.*

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

        holder.itemView.setOnClickListener {
            val image = imagesList[position].originalUrl
            val imageFixed = imagesList[position].fixedHeightDownsampled
            val title = imagesList[position].title

            val extras = FragmentNavigatorExtras(
                it.imageUrl to image
            )
            val next = FavoritesFragmentDirections.actionFavoritesToGiphDialog()
                .apply {
                    this.image = imageFixed
                    this.id = imagesList[position].uid
                    this.imageOriginal = image
                    this.title = title
                }
            it.findNavController().navigate(next, extras)
        }
    }

    fun addAll(newImageList: List<Image>) {
        imagesList.addAll(newImageList)
        notifyItemInserted(imagesList.size - 1)
    }
}
