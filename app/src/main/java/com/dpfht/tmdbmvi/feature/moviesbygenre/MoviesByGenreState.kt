package com.dpfht.tmdbmvi.feature.moviesbygenre

import androidx.navigation.NavDirections

sealed class MoviesByGenreState {

  data class NotifyItemInserted(val value: Int = 0): MoviesByGenreState()
  data class IsLoading(val value: Boolean = false): MoviesByGenreState()
  data class ErrorMessage(val message: String = ""): MoviesByGenreState()
  data class NavigateToNextScreen(val directions: NavDirections): MoviesByGenreState()
  object Idle: MoviesByGenreState()
}
