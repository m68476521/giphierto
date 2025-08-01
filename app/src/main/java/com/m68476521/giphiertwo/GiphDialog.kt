package com.m68476521.giphiertwo

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.m68476521.giphiertwo.data.Image
import com.m68476521.giphiertwo.databinding.GiphFragmentBinding
import com.m68476521.giphiertwo.models.LocalImagesViewModel
import com.m68476521.giphiertwo.util.shortSnackBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

class GiphDialog : DialogFragment() {
    private val args: GiphDialogArgs by navArgs()
    private lateinit var favoritesModel: LocalImagesViewModel
    private lateinit var gifDrawable: GifDrawable
    private lateinit var binding: GiphFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = GiphFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
            LinearLayout.LayoutParams.MATCH_PARENT,
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        favoritesModel = ViewModelProvider(this).get(LocalImagesViewModel::class.java)

        postponeEnterTransition()
        binding.image.load(args.imageOriginal) {
            startPostponedEnterTransition()
        }

        binding.close.setOnClickListener { it.findNavController().popBackStack() }

        binding.toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addToFavorite(args.id, args.imageOriginal, args.image, args.title)
            } else {
                removeFromFavoritesById(args.id)
            }
        }

        imageById(args.id)
        binding.shareIcon.setOnClickListener { checkPermission(gifDrawable) }
    }

    private fun ImageView.load(
        url: String,
        loadOnlyFromCache: Boolean = false,
        onLoadingFinished: () -> Unit = {},
    ) {
        val listener =
            object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable?>,
                    isFirstResource: Boolean,
                ): Boolean {
                    onLoadingFinished()
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable?>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean,
                ): Boolean {
                    if (resource != null) {
                        gifDrawable = resource
                    }

                    onLoadingFinished()
                    return false
                }
            }

        val requestOptions =
            RequestOptions
                .placeholderOf(R.drawable.ic_launcher_background)
                .dontTransform()
                .onlyRetrieveFromCache(loadOnlyFromCache)

        this.apply {
            transitionName = url
            Glide
                .with(requireContext())
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions)
                .listener(listener)
                .into(
                    object : ImageViewTarget<GifDrawable>(this) {
                        override fun setResource(resource: GifDrawable?) {
                            this.setDrawable(resource)
                        }
                    },
                )
        }
    }

    private fun checkPermission(gifDrawable: GifDrawable) {
        Dexter
            .withContext(requireContext())
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(
                object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        saveImageAndShare(gifDrawable)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        requireView().shortSnackBar(getString(R.string.permissionNotGranted))
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?,
                    ) {
                        token?.continuePermissionRequest()
                    }
                },
            ).check()
    }

    private fun saveImageAndShare(gifDrawable: GifDrawable?) {
        gifDrawable?.let {
            val baseDir = requireContext().getExternalFilesDir(null)
            val fileName = "${args.title}.gif"

            val sharingGifFile = File(baseDir, fileName)
            gifDrawableToFile(gifDrawable, sharingGifFile)

            val uri: Uri =
                FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    sharingGifFile,
                )

            shareFile(uri)
        }
    }

    private fun shareFile(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/gif"

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "Share Emoji"))
    }

    private fun gifDrawableToFile(
        gifDrawable: GifDrawable,
        gifFile: File,
    ) {
        val byteBuffer = gifDrawable.buffer
        val output = FileOutputStream(gifFile)
        val bytes = ByteArray(byteBuffer.capacity())
        (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
        output.write(bytes, 0, bytes.size)
        output.close()
    }

    private fun addToFavorite(
        id: String,
        imageOriginal: String,
        imageFixed: String,
        title: String,
    ) {
        val newImage =
            Image(
                uid = id,
                fixedHeightDownsampled = imageFixed,
                originalUrl = imageOriginal,
                title = title,
            )
        // TODO fix this
        GlobalScope.launch {
            favoritesModel.insert(newImage)
        }
    }

    private fun removeFromFavoritesById(id: String) {
        // TODO fix this
        GlobalScope.launch {
            favoritesModel.deleteById(id)
        }
    }

    private fun imageById(id: String) {
        lifecycleScope.launch {
            val result = favoritesModel.imageById(id)
            if (result != null) {
                binding.toggleFavorite.isChecked = true
            }
        }
    }
}
