package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.domain.model.GetMovieByGenreResult

interface GetMovieByGenreUseCase {

  suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieByGenreResult>
}
