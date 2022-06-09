package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.domain.model.GetMovieReviewResult

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieReviewResult>
}
