package com.m68476521.giphierto.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m68476521.giphierto.api.Data
import com.m68476521.giphierto.api.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {
    val categories: MutableLiveData<List<Data>> by lazy {
        MutableLiveData<List<Data>>()
    }

    init {
        viewModelScope.launch {
            categories.value = mainRepository.getCategories().data
        }
    }

    fun getCategories(): LiveData<List<Data>> {
        return categories
    }
}
