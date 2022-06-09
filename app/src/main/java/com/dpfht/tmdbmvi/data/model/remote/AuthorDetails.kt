package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class AuthorDetails(
    val name: String? = null,
    val username: String? = null,

    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = null,

    val rating: Float = 0.0f
)
