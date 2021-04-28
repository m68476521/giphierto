package com.m68476521.giphierto.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.R
import com.m68476521.giphierto.data.Image
import com.m68476521.giphierto.databinding.ImageItemBinding
import kotlinx.android.synthetic.main.image_item.view.*

class FavoriteAdapter : ListAdapter<Image, FavoriteAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position)
        holder.apply {
            bind(image, context)
            itemView.tag = image
        }

        holder.itemView.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                it.imageUrl to image.originalUrl
            )
            val direction = FavoritesFragmentDirections.actionFavoritesToGiphDialog() // (id, name)
                .apply {
                    this.image = image.fixedHeightDownsampled
                    this.id = image.uid
                    this.imageOriginal = image.originalUrl
                    this.title = image.title
                }
            it.findNavController().navigate(direction, extras)
        }
    }

    class ViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image, context: Context) {
            binding.apply {
                image = item
                val imageData = item.fixedHeightDownsampled
                imageUrl.apply {
                    transitionName = imageData
                    Glide
                        .with(context)
                        .asGif()
                        .load(imageData)
                        .fitCenter()
                        .placeholder(R.drawable.giphy_icon)
                        .dontTransform()
                        .into(itemView.imageUrl)
                }

                executePendingBindings()
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.uid == newItem.uid
    }
}
