package com.m68476521.giphiertwo.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m68476521.giphiertwo.api.CategoryData
import com.m68476521.giphiertwo.api.MainRepository
import com.m68476521.giphiertwo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel
    @Inject
    constructor(
        mainRepository: MainRepository,
    ) : ViewModel() {
        private val categories = MutableLiveData<Resource<CategoryData>>()

        val categoriesData: LiveData<Resource<CategoryData>>
            get() = categories

        init {
            viewModelScope.launch {
                categories.postValue(Resource.loading(null))
                mainRepository.getCategories().let { response ->
                    if (response.isSuccessful) {
                        categories.postValue(Resource.success(response.body()))
                    } else {
                        categories.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            }
        }
    }
