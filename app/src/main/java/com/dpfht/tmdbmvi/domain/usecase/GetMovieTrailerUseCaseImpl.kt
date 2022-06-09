package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.domain.model.GetMovieTrailerResult
import javax.inject.Inject

class GetMovieTrailerUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult> {
    return appRepository.getMovieTrailer(movieId)
  }
}
