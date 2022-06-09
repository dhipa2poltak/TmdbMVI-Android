package com.dpfht.tmdbmvi.feature.moviesbygenre

sealed class MoviesByGenreIntent {

  object FetchMovie: MoviesByGenreIntent()
  object FetchNextMovie: MoviesByGenreIntent()
  data class NavigateToNextScreen(val position: Int): MoviesByGenreIntent()
  object EnterIdleState: MoviesByGenreIntent()
}