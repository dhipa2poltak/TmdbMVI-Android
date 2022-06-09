package com.dpfht.tmdbmvi.data.repository

import com.dpfht.tmdbmvi.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvi.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvi.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvi.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper

interface AppRepository {

  suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult>

  suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult>

  suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult>

  suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult>

  suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult>
}
