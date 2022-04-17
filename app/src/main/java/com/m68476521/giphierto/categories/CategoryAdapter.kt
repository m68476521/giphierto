package com.m68476521.giphierto.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.R
import com.m68476521.giphierto.api.Data
import com.m68476521.giphierto.databinding.CategoryItemBinding

class CategoryAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var categoryList: List<Data> = emptyList()

    private lateinit var context: Context
    private var isFromCategory = true

    constructor(isFromCategory: Boolean) : this() {
        this.isFromCategory = isFromCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        context = parent.context
        return CategoryHolder.create(parent = parent)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = categoryList[position].gif.images.fixedHeightDownSampled.url ?: return

        (holder as CategoryHolder).bind(image, categoryList[position].name, context)

        holder.itemView.setOnClickListener {
            val next = if (isFromCategory)
                CategoriesFragmentDirections.actionSearchToSubCategoryFragment()
                    .apply {
                        this.subcategory = categoryList[position].nameEncoded
                    }
            else
                SubCategoryFragmentDirections.actionSubCategoryFragmentToSubCategorySelectedFragment()
                    .apply {
                        this.category = categoryList[position].nameEncoded
                    }
            it.findNavController().navigate(next)
        }
    }

    fun swapCategories(categories: List<Data>) {
        if (this.categoryList === categories)
            return

        this.categoryList = categories
        // TODO fix this
        this.notifyDataSetChanged()
    }
}

class CategoryHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: String, categoryName: String, context: Context) {

        Glide
            .with(context)
            .asGif()
            .load(image)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_background)
            .dontTransform()
            .into(binding.imageUrl)

        //TODO fix this
        binding.categoryName.text = categoryName.toUpperCase()
    }

    companion object {
        fun create(parent: ViewGroup): CategoryHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
            val binding = CategoryItemBinding.bind(view)
            return CategoryHolder(binding)
        }
    }
}
