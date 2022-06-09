package com.dpfht.tmdbmvi.feature.moviereviews

sealed class MovieReviewsIntent {

  object FetchReview: MovieReviewsIntent()
  object FetchNextReview: MovieReviewsIntent()
  object EnterIdleState: MovieReviewsIntent()
}