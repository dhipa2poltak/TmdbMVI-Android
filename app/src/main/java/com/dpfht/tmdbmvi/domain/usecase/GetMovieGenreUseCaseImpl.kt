package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.domain.model.GetMovieGenreResult
import javax.inject.Inject

class GetMovieGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
