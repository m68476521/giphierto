package com.m68476521.giphiertwo.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.shortSnackBar(message: CharSequence) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}
