package util

import com.morozco.core.model.Image

/**
 * Extension to retrieve the fixed-height URL if available,
 * falling back to the original URL.
 */
fun Image.getPreferredUrl(): String {
    return images.fixedHeight?.url ?: images.original.url
}