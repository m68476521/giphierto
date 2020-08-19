package com.m68476521.giphierto.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m68476521.giphierto.R
import com.m68476521.giphierto.api.Data
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter() : RecyclerView.Adapter<CategoryHolder>() {
    private var categoryList: List<Data> = emptyList()

    private lateinit var context: Context
    private var isFromCategory = true

    constructor(isFromCategory: Boolean) : this() {
        this.isFromCategory = isFromCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        context = parent.context
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val image = categoryList[position].gif.images.fixedHeightDownSampled.url ?: return
        holder.bind(
            image,
            categoryList[position].name,
            context
        )

        holder.itemView.setOnClickListener {
            val next = if (isFromCategory)
                CategoriesFragmentDirections.actionSearchFragment2ToSubCategoryFragment()
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
        this.notifyDataSetChanged()
    }
}

class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(image: String, categoryName: String, context: Context) {
        Glide
            .with(context)
            .asGif()
            .load(image)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_background)
            .dontTransform()
            .into(itemView.imageUrl)

        itemView.categoryName.text = categoryName.toUpperCase()
    }
}
