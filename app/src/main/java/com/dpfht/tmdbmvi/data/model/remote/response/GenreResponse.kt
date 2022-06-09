package com.dpfht.tmdbmvi.data.model.remote.response

import com.dpfht.tmdbmvi.data.model.remote.Genre
import com.dpfht.tmdbmvi.domain.model.GetMovieGenreResult

data class GenreResponse(
    val genres: List<Genre>? = null
)

fun GenreResponse.toDomain() = GetMovieGenreResult(genres ?: arrayListOf())
