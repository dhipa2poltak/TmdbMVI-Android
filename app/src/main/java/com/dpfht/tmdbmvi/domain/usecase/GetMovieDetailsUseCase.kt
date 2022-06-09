package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult>
}
