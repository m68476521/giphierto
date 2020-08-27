package com.m68476521.giphierto

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.m68476521.giphierto.data.Image
import com.m68476521.giphierto.models.LocalImagesViewModel
import kotlinx.android.synthetic.main.giph_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GiphDialog : DialogFragment() {
    private val args: GiphDialogArgs by navArgs()
    private lateinit var favoritesModel: LocalImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.giph_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesModel = ViewModelProvider(this).get(LocalImagesViewModel::class.java)

        postponeEnterTransition()
        image.load(args.image) {
            startPostponedEnterTransition()
        }

        constraint.setOnClickListener { dialog?.dismiss() }
        close.setOnClickListener { dialog?.dismiss() }
        image.setOnClickListener { }
        toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                addToFavorite(args.id, args.image)
            else
                removeFromFavoritesById(args.id)
        }

        imageById(args.id)
    }

    private fun ImageView.load(
        url: String,
        loadOnlyFromCache: Boolean = false,
        onLoadingFinished: () -> Unit = {}
    ) {
        val listener = object : RequestListener<GifDrawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        }

        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
            .dontTransform()
            .onlyRetrieveFromCache(loadOnlyFromCache)

        this.apply {
            transitionName = url
            Glide
                .with(requireContext())
                .asGif()
                .load(url)
                .apply(requestOptions)
                .listener(listener)
                .into(object : ImageViewTarget<GifDrawable>(this) {
                    override fun setResource(resource: GifDrawable?) {
                        this.setDrawable(resource)
                    }
                })
        }
    }

    private fun addToFavorite(id: String, image: String) {
        val newImage = Image(id, image)
        GlobalScope.launch {
            favoritesModel.insert(newImage)
        }
    }

    private fun removeFromFavoritesById(id: String) {
        GlobalScope.launch {
            favoritesModel.deleteById(id)
        }
    }

    private fun imageById(id: String) {
        GlobalScope.launch {
            val result = favoritesModel.imageById(id)
            if (result != null)
                toggleFavorite.isChecked = true
        }
    }
}
