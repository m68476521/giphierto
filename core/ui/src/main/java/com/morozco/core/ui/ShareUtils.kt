package com.morozco.core.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

object ShareUtils {
    fun shareImage(context: Context, scope: CoroutineScope, url: String, title: String) {
        scope.launch {
            val file = downloadImage(context, url)
            if (file != null) {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                shareFile(context, uri, title)
            }
        }
    }

    private suspend fun downloadImage(context: Context, urlString: String): File? = withContext(Dispatchers.IO) {
        try {
            // Using a timestamped filename can sometimes help apps recognize it's a new unique file
            val file = File(context.cacheDir, "shared_giphy_${System.currentTimeMillis()}.gif")
            // Clean up old shared gifs first
            context.cacheDir.listFiles { _, name -> name.startsWith("shared_giphy_") }?.forEach { it.delete() }
            
            val url = URL(urlString)
            url.openStream().use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: Exception) {
            null
        }
    }

    private fun shareFile(context: Context, uri: Uri, title: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/gif"
            putExtra(Intent.EXTRA_STREAM, uri)
            // Some apps prefer EXTRA_TITLE or no text when sharing a single file to treat it correctly as media
            putExtra(Intent.EXTRA_SUBJECT, title)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // Specifically suggest it's a GIF
            clipData = android.content.ClipData.newRawUri(null, uri)
        }
        val chooser = Intent.createChooser(intent, "Share Giph")
        context.startActivity(chooser)
    }
}
