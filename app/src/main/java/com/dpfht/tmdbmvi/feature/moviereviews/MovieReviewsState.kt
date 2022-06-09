package com.dpfht.tmdbmvi.feature.moviereviews

sealed class MovieReviewsState {

  data class NotifyItemInserted(val value: Int = 0): MovieReviewsState()
  data class IsLoading(val value: Boolean = false): MovieReviewsState()
  data class ErrorMessage(val message: String = ""): MovieReviewsState()
  object Idle: MovieReviewsState()
}