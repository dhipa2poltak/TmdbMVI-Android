package com.dpfht.tmdbmvi.domain.model

import com.dpfht.tmdbmvi.data.model.remote.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
