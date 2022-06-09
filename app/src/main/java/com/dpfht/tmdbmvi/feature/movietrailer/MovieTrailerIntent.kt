package com.dpfht.tmdbmvi.feature.movietrailer

sealed class MovieTrailerIntent {

  object FetchTrailer: MovieTrailerIntent()
  object EnterIdleState: MovieTrailerIntent()
}
