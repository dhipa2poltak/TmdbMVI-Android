package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.domain.model.GetMovieReviewResult
import javax.inject.Inject

class GetMovieReviewUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieReviewResult> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
