package com.dpfht.tmdbmvi.domain.model

import com.dpfht.tmdbmvi.data.model.remote.Review

data class GetMovieReviewResult(
  val reviews: List<Review>,
  val page: Int
)
