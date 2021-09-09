package com.example.paginglibrary.api.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    val id: String,
    val description: String?,
    val urls: PhotoUrls,
    val user: User
) : Parcelable {

    @Parcelize
    data class PhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class User(
        val name: String,
        val username: String
    ) : Parcelable
}