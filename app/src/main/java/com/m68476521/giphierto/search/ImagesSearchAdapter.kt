package com.m68476521.giphierto.search

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
import kotlinx.android.synthetic.main.image_item.view.*

class ImagesSearchAdapter :
    PagingDataAdapter<Image, ImagesSearchAdapter.ImageHolder>(ImageComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = getItem(position)
        val imageForPreview = item?.images?.fixedHeightDownsampled?.url ?: return
        holder.bind(imageForPreview)

        holder.itemView.cardView.setOnClickListener {
            val imageForDetails = item.images.fixedHeight.url
            val title = item.title

            val extras = FragmentNavigatorExtras(
                it.imageUrl to imageForDetails
            )

            val next =
                SearchFragmentDirections.actionSearchFragmentToGiphDialog()
                    .apply {
                        this.image = imageForPreview
                        id = getItem(position)?.id.toString()
                        imageOriginal = imageForDetails
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
