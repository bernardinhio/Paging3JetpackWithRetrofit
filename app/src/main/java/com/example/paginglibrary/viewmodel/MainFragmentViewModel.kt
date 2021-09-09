package com.example.paginglibrary.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginglibrary.api.api.PhotoModel
import com.example.paginglibrary.data.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// We want all its Parameters to be Injected, so here we
// want the repository Singleton parameter to be injected
@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    fun giveMeLiveDataOfPagingDataOfPhotos(query: String): LiveData<PagingData<PhotoModel>>{
        return repository.getPhotosPagingDataFlow(query).asLiveData()
    }


}
