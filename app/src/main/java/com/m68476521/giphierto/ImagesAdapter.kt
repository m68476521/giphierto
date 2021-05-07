package com.m68476521.giphierto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.databinding.ImageItemBinding
import com.m68476521.giphierto.home.TrendingFragmentDirections
import kotlinx.android.synthetic.main.image_item.view.*

class ImagesAdapter : PagingDataAdapter<Image, ImagesAdapter.ImageHolder>(ImageComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = getItem(position)
        val imagePreview = item?.images?.fixedHeightDownsampled?.url ?: return
        holder.bind(imagePreview)

        holder.itemView.cardView.setOnClickListener {
            val imageForDetails = item.images.fixedHeight.url
            val title = item.title
            val extras = FragmentNavigatorExtras(
                it.imageUrl to imageForDetails
            )

            val next =
                TrendingFragmentDirections.actionTrendingToGiphDialog()
                    .apply {
                        this.image = imagePreview
                        id = getItem(position)?.id.toString()
                        this.imageOriginal = imageForDetails
                        this.title = title
                    }

            it.findNavController().navigate(next, extras)
        }
    }

    inner class ImageHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.imageUrl.apply {
                transitionName = image
                Glide
                    .with(context)
                    .asGif()
                    .load(image)
                    .fitCenter()
                    .placeholder(R.drawable.giphy_icon)
                    .dontTransform()
                    .into(binding.imageUrl)
            }
        }
    }
}

object ImageComparator : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}
