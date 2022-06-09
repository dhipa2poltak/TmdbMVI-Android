package com.dpfht.tmdbmvi.data.repository

import com.dpfht.tmdbmvi.data.api.RestService
import com.dpfht.tmdbmvi.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvi.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvi.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvi.data.model.remote.response.toDomain
import com.dpfht.tmdbmvi.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvi.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvi.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvi.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbmvi.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
  private val restService: RestService
): AppRepository {

  override suspend fun getMovieGenre():  UseCaseResultWrapper<GetMovieGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }
}
