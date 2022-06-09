package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Movie(
    val adult: Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = null,

    val id: Int = 0,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Float = 0.0f,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    val title: String? = null,
    val video: Boolean = false,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float = 0.0f,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0
)
