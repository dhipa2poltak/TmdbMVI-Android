package com.dpfht.tmdbmvi.domain.model

import com.dpfht.tmdbmvi.data.model.remote.Genre

data class GetMovieGenreResult(
  val genres: List<Genre>
)
