package com.m68476521.giphierto.models

import androidx.lifecycle.ViewModel
import com.m68476521.giphierto.api.Data

class CategoryViewModel : ViewModel() {
    var categories: List<Data> = emptyList()
}