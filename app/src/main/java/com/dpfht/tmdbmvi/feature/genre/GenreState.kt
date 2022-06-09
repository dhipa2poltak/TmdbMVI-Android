package com.dpfht.tmdbmvi.feature.genre

import androidx.navigation.NavDirections

sealed class GenreState {

  data class NotifyItemInserted(val value: Int = 0): GenreState()
  data class IsLoading(val value: Boolean = false): GenreState()
  data class ErrorMessage(val message: String = ""): GenreState()
  data class NavigateToNextScreen(val directions: NavDirections): GenreState()
  object Idle: GenreState()
}
