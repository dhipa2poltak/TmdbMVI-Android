package com.dpfht.tmdbmvi.feature.moviedetails

import androidx.navigation.NavDirections

sealed class MovieDetailsState {

  data class IsLoading(val value: Boolean = false): MovieDetailsState()
  data class ErrorMessage(val message: String = ""): MovieDetailsState()
  data class ViewDetails(val title: String, val overview: String, val imageUrl: String): MovieDetailsState()
  data class NavigateToReviewScreen(val directions: NavDirections): MovieDetailsState()
  data class NavigateToTrailerScreen(val movieId: Int): MovieDetailsState()
  object Idle: MovieDetailsState()
}