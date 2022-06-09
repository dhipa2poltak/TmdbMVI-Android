package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.domain.model.GetMovieTrailerResult

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult>
}
