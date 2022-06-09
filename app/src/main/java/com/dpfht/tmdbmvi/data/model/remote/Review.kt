package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

@Suppress("unused")
data class Review(
    val author: String? = null,

    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetails? = null,

    val content: String? = null,

    @SerializedName("created_at")
    @Expose
    val createdAt: Date? = null,

    val id: String? = null,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: Date? = null,

    val url: String? = null
)
