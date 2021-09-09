package com.example.paginglibrary.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.paginglibrary.api.RetrofitApi
import com.example.paginglibrary.api.api.PhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    val retrofitApi: RetrofitApi
) {




    // return Flow
    fun getPhotosPagingDataFlow(query: String): Flow<PagingData<PhotoModel>>{

        val pagingConfig = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        )

        /**
         * The pagingSourceFactory is a lambda returning our PagingSource.
         * This Lambda will be used by the Pager to check and
         * confirm that the PagingSource used is doing the loading in suspend
         * or in Coroutine backend thread, else it will create for us
         * a SuspendingPagingSourceFactory where the loading is done on a
         * Coroutine Dispatcher.
         *
         * This check is necessary for old versions of Library Pager because
         * legacy paging source implementation was that data source was created
         * on the given thread that we had to specify
         */
        val lambdaPagingSourceToCheckSuspend: () -> PhotosPagingSource =
            { PhotosPagingSource(retrofitApi, query) }

        // Pager is reactive stream of PagingData
        val photosPager: Pager<Int, PhotoModel> = Pager<Int, PhotoModel>(
            config = pagingConfig,
            pagingSourceFactory = lambdaPagingSourceToCheckSuspend
        )

        // pager.flow is a Native Property of Pager !!!!!!!!!!!!
        val flowOfPhotosPagingData: Flow<PagingData<PhotoModel>> = photosPager.flow
        
        return flowOfPhotosPagingData
    }




}