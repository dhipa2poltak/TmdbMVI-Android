package com.dpfht.tmdbmvi.feature.genre

sealed class GenreIntent {

  object FetchGenre: GenreIntent()
  data class NavigateToNextScreen(val position: Int): GenreIntent()
  object EnterIdleState: GenreIntent()
}
