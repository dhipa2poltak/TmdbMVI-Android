package com.dpfht.tmdbmvi.domain.model

import com.dpfht.tmdbmvi.data.model.remote.Movie

data class GetMovieByGenreResult(
  val movies: List<Movie>,
  val page: Int
)
