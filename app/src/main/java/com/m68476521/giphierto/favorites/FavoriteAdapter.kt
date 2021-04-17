package com.m68476521.giphierto.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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
            bind(
                createOnClickListener(
                    image.originalUrl, image.fixedHeightDownsampled,
                    image.title, image.uid
                ),
                image, context
            )
            itemView.tag = image
        }
    }

    private fun createOnClickListener(
        originalUrl: String,
        fixedHeightDownsampled: String,
        title: String,
        id: String
    ): View.OnClickListener {
        return View.OnClickListener {
            val extras = FragmentNavigatorExtras(
                it.imageUrl to originalUrl
            )
            val direction = FavoritesFragmentDirections.actionFavoritesToGiphDialog() // (id, name)
                .apply {
                    this.image = fixedHeightDownsampled
                    this.id = id
                    this.imageOriginal = originalUrl
                    this.title = title
                }
            it.findNavController().navigate(direction, extras)
        }
    }

    class ViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: Image, context: Context) {
            binding.apply {
                clickListener = listener
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
