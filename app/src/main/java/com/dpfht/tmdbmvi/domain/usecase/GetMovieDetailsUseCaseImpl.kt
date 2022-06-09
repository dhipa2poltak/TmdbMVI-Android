package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
