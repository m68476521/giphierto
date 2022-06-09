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

class FavoriteAdapter : ListAdapter<Image, RecyclerView.ViewHolder>(DiffCallback()) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = getItem(position)
        (holder as ViewHolder).apply {
            bind(image, context)
            itemView.tag = image
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
                        .into(binding.imageUrl)
                }

                executePendingBindings()
            }
            binding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.imageUrl to item.originalUrl
                )
                val direction = FavoritesFragmentDirections.actionFavoritesToGiphDialog() // (id, name)
                    .apply {
                        this.image = item.fixedHeightDownsampled
                        this.id = item.uid
                        this.imageOriginal = item.originalUrl
                        this.title = item.title
                    }
                it.findNavController().navigate(direction, extras)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_item, parent, false)
                val binding = ImageItemBinding.bind(view)
                return ViewHolder(binding)
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
