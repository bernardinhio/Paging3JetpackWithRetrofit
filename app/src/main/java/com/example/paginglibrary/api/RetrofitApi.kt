package com.example.paginglibrary.api

import com.example.paginglibrary.BuildConfig
import com.example.paginglibrary.api.api.MyPageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {

    /**
     * Example The Unsplash API - Search Photos requests docs
     * https://unsplash.com/documentation#search-photos
     * Register project and get access Key:
     * https://unsplash.com/oauth/applications
     *
     * Using the Postman and entering the Header with the Access-Key,
     * we can simulate:
     * Results by keyword (or Query):
     * https://api.unsplash.com/search/photos?query=cats
     *
     * BY DEFAULT IN THIS API
     * page = 1
     * per_page = 10
     *
     *
     * This means that, if we don't specify these parans, we will get
     * page = 1 and per_page = 10
     * So:
     * https://api.unsplash.com/search/photos?query=cats
     * IS SAME AS:
     * https://api.unsplash.com/search/photos?query=cats&page=1
     *
     *
     * The "query" parameter is mandatory !!!!
     * Results by Keyword and Page number:
     * https://api.unsplash.com/search/photos?query=cats&page=1
     * Or
     * https://api.unsplash.com/search/photos?query=cats&page=2
     * Or
     * https://api.unsplash.com/search/photos?query=cats&page=3
     *
     * Results by Keyword and Page number and number of results per page:
     * https://api.unsplash.com/search/photos?query=cats&page=1&per_page=12
     * Or
     * https://api.unsplash.com/search/photos?query=cats&page=1&per_page=23
     *
     */
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val END_POINT = "search/photos"
        const val CLIENT_ID = BuildConfig.UNSPLASH_API_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET(END_POINT)
    suspend fun getPhotosPageResult(
        @Query("query") query: String  // Mandatory
    ): Response<MyPageModel>

    // Overload
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET(END_POINT)
    suspend fun getPhotosPageResult(
        @Query("query") query: String,  // Mandatory
        @Query("page") page: Int,       // Optional  // default = 1
        @Query("per_page") perPage: Int // Optional  // default = 10
    ): Response<MyPageModel>

}