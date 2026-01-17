package com.m68476521.giphiertwo.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m68476521.giphiertwo.api.CategoryData
import com.m68476521.giphiertwo.api.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubcategoryViewModel
    @Inject
    constructor(
        private val mainRepository: MainRepository,
    ) : ViewModel() {
        var category = ""

        private val subCategories: MutableLiveData<CategoryData> by lazy {
            MutableLiveData<CategoryData>()
        }

        fun querySubCategories(categorySelected: String) {
            viewModelScope.launch {
                subCategories.value = mainRepository.getSubCategories(categorySelected)
            }
        }

        fun getSubCategories(): LiveData<CategoryData> = subCategories
    }
