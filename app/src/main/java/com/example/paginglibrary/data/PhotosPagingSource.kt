package com.example.paginglibrary.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginglibrary.api.RetrofitApi
import com.example.paginglibrary.api.api.MyPageModel
import com.example.paginglibrary.api.api.PhotoModel
import kotlinx.coroutines.delay
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Usually we don't use DaggerHilt with PagingSource when using it with
 * Retrofit. Simply because in the PagingSource constructor we want to
 * pass a Query String that is used inside the PagingSource class and
 * this Query String cannot be known during the compile-time and DaggerHilt
 * knows the value of its dependencies during the compile-time but we
 * will know the Query value later during the Run-time when the App
 * decides which Query we want to have at the moment of usage. Se we
 * don't have to use @Inject constructor()
 */
class PhotosPagingSource(
    private val retrofitApi: RetrofitApi,
    private val query: String
) : PagingSource<Int, PhotoModel>() {

    var loadRournd = 0

    // Do the retrofit call here
    // This function is called everytime we load a new Page, so everytime we need
    // to make a retrofit call and ask retrofit to load the current Page index!!!
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {

        // Just for me debugging
        loadRournd++
        Log.d("pagingLog", "load() of PagingSource Round: $loadRournd")

        val currentPageIndex: Int = params.key ?: 1 // We get from params
        val numPagesToLoad: Int = params.loadSize  // We get from params

        try {
            val response: Response<MyPageModel> =
                retrofitApi.getPhotosPageResult(query, currentPageIndex, numPagesToLoad)

            if (response.code() == HttpURLConnection.HTTP_OK) {

                // Every time we load a Page we need 3 Params to create a LoadingResult.Page()
                val listOfPhotos: List<PhotoModel>? = response.body()?.results
                val prevKey = if (currentPageIndex == 1) null else currentPageIndex - 1
                val nextKey = if (listOfPhotos.isNullOrEmpty()) null else (currentPageIndex + 1)

                // Just for me to simulate debugging and artificially simulate
                // late in loading or slow Internet
                delay(5000)
                Log.d("pagingLog", "prevPage $prevKey")
                Log.d("pagingLog", "currPage: $currentPageIndex")
                Log.d("pagingLog", "nextPage: $nextKey")
                Log.d("pagingLog", "listSize: ${listOfPhotos?.size}")

                if (!listOfPhotos.isNullOrEmpty()) {
                    Log.d("pagingLog", "List: Not Null or Empty")
                    return LoadResult.Page<Int, PhotoModel>(listOfPhotos, prevKey, nextKey)
                    // The Error that we set here is used in the LoadStateAdapter to we
                    // can modify UI or show message
                } else if(currentPageIndex == 1){
                    Log.d("pagingLog", "List: No Initial Results")
                    // This Error will not be used inside the RecyclerView but outside
                    return LoadResult.Error<Int, PhotoModel>(Throwable(ResponseMessage.NO_INITIAL_RESULTS.message))
                } else { // if(currentPageIndex > 1)
                    Log.d("pagingLog", "List: List of Items reached End")
                    return LoadResult.Error<Int, PhotoModel>(Throwable(ResponseMessage.LIST_OF_RESULTS_REACHED_END.message))
                }

            } else if(response.code() == HttpURLConnection.HTTP_NO_CONTENT){
                return LoadResult.Error(Throwable(ResponseMessage.NO_INITIAL_RESULTS.message))
            } else return LoadResult.Error(Throwable(ResponseMessage.ERROR_SERVER_BROKEN.message))


        } catch (e: IOException) {
            // IOException thrown for Internet problems
            // The Error that we set here is used in the LoadStateAdapter to we
            // can modify UI or show message
            return LoadResult.Error(Throwable(ResponseMessage.ERROR_NO_INTERNET.message))
        } catch (exception: HttpException) {
            // HttpException Thrown for any parsing or Retrofit Exceptions
            // The Error that we set here is used in the LoadStateAdapter to we
            // can modify UI or show message
            return LoadResult.Error(Throwable(ResponseMessage.ERROR_SERVER_BROKEN.message))
        }

    }



    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int? {
        TODO("Not yet implemented")
    }
}

// The Error that we set here is used in the LoadStateAdapter to we
// can modify UI or show message
enum class ResponseMessage(val message: String) {
    LIST_OF_RESULTS_REACHED_END("The List has ended !"),
    ERROR_NO_INTERNET("Error: Check Internet connection !"),
    NO_INITIAL_RESULTS("No results for your search, try something else."),
    ERROR_SERVER_BROKEN("Something bad happened, server is broken")
}