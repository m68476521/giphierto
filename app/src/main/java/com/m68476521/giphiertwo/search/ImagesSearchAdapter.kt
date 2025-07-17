package com.m68476521.giphiertwo.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphiertwo.ImageComparator
import com.m68476521.giphiertwo.R
import com.m68476521.giphiertwo.api.Image
import com.m68476521.giphiertwo.databinding.ImageItemBinding

class ImagesSearchAdapter : PagingDataAdapter<Image, RecyclerView.ViewHolder>(ImageComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageHolder.create(parent = parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.images?.fixedHeightDownsampled?.url ?: return
        (holder as ImageHolder).bind(item)
    }

    class ImageHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.imageUrl.apply {
                transitionName = image.images?.fixedHeightDownsampled?.url
                Glide
                    .with(context)
                    .asGif()
                    .load(image.images?.fixedHeightDownsampled?.url)
                    .fitCenter()
                    .placeholder(R.drawable.giphy_icon)
                    .dontTransform()
                    .into(binding.imageUrl)
            }

            binding.root.setOnClickListener {
                val imageForDetails = image.images.fixedHeight.url
                val title = image.title

                val extras = FragmentNavigatorExtras(
                    binding.imageUrl to imageForDetails
                )

                val next =
                    SearchFragmentDirections.actionSearchFragmentToGiphDialog()
                        .apply {
                            this.image = image.images?.fixedHeightDownsampled?.url ?: return@apply
                            id = image.id
                            imageOriginal = imageForDetails
                            this.title = title
                        }
                it.findNavController().navigate(next, extras)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ImageHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_item, parent, false)
                val binding = ImageItemBinding.bind(view)
                return ImageHolder(binding)
            }
        }
    }
}
