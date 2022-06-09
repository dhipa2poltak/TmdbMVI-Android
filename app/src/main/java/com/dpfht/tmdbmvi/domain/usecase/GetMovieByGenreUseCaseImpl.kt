package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.domain.model.GetMovieByGenreResult
import javax.inject.Inject

class GetMovieByGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
