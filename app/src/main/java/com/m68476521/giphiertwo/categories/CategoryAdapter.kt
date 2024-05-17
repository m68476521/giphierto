package com.m68476521.giphiertwo.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.composethemeadapter.MdcTheme
import com.m68476521.giphiertwo.R
import com.m68476521.giphiertwo.api.Data
import com.m68476521.giphiertwo.databinding.CategoryItemBinding

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

        binding.categoryName.setContent {
            MdcTheme {
                Category(categoryName.uppercase())
            }
        }
    }

    @Composable
    private fun Category(categoryName: String) {
        Text(
            text = categoryName,
            color = Color.White,
            fontSize = 16.sp,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
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
