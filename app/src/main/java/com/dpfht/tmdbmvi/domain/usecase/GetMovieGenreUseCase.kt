package com.dpfht.tmdbmvi.domain.usecase

import com.dpfht.tmdbmvi.domain.model.GetMovieGenreResult

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult>
}
