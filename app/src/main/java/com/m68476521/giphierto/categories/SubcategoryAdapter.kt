package com.m68476521.giphierto.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.ImageComparator
import com.m68476521.giphierto.R
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.databinding.ImageItemBinding

class SubcategoryAdapter :
    PagingDataAdapter<Image, RecyclerView.ViewHolder>(ImageComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.images?.fixedHeightDownsampled?.url ?: return

        (holder as ImageHolder).bind(item)
    }

    inner class ImageHolder(private val binding: ImageItemBinding) :
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
            }.setOnClickListener {
                val imageForDetails = image.images.fixedHeight.url
                val title = image.title
                val extras = FragmentNavigatorExtras(
                    binding.imageUrl to imageForDetails
                )
                val next =
                    SubCategorySelectedFragmentDirections.actionSubCategorySelectedFragmentToGiphDialog()
                        .apply {
                            this.image = image.images?.fixedHeightDownsampled?.url ?: return@apply
                            id = image?.id.toString()
                            this.imageOriginal = imageForDetails
                            this.title = title
                        }

                it.findNavController().navigate(next, extras)
            }
        }
    }
}
