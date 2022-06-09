package com.dpfht.tmdbmvi.data.model.remote.response

import com.dpfht.tmdbmvi.data.model.remote.Movie
import com.dpfht.tmdbmvi.domain.model.GetMovieByGenreResult
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class DiscoverMovieByGenreResponse(
  val page: Int = 0,
  val results: List<Movie>? = null,

  @SerializedName("total_pages")
  @Expose
  val totalPages: Int = 0,

  @SerializedName("total_results")
  @Expose
  val totalResults: Int = 0
)

fun DiscoverMovieByGenreResponse.toDomain() = GetMovieByGenreResult(
  this.results ?: arrayListOf(),
  this.page
)
