package com.dpfht.tmdbmvi.feature.moviedetails

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbmvi.Config
import com.dpfht.tmdbmvi.base.BaseViewModel
import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvi.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.Success
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsIntent.EnterIdleState
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsIntent.FetchDetails
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsIntent.NavigateToReviewScreen
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsIntent.NavigateToTrailerScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): BaseViewModel<MovieDetailsIntent, MovieDetailsState>() {

  override val mState = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Idle)

  private var _movieId = -1
  private var title = ""
  private var overview = ""
  private var imageUrl = ""

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchDetails -> {
            start()
          }
          NavigateToReviewScreen -> {
            navigateToReviewScreen(_movieId, title)
          }
          NavigateToTrailerScreen -> {
            navigateToTrailerScreen(_movieId)
          }
          EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    } else {
      mState.value = MovieDetailsState.ViewDetails(title, overview, imageUrl)
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch(Dispatchers.Main) {
      mState.value = MovieDetailsState.IsLoading(true)
      when (val result = getMovieDetailsUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(result: GetMovieDetailsResult) {
    imageUrl = ""
    if (result.posterPath.isNotEmpty()) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + result.posterPath
    }

    _movieId = result.movieId
    title = result.title
    overview = result.overview

    mState.value = MovieDetailsState.ViewDetails(title, overview, imageUrl)
    mState.value = MovieDetailsState.IsLoading(false)
  }

  private fun onError(message: String) {
    mState.value = MovieDetailsState.IsLoading(false)
    mState.value = MovieDetailsState.ErrorMessage(message)
  }

  private fun navigateToReviewScreen(movieId: Int, title: String) {
    viewModelScope.launch {
      mState.value = MovieDetailsState.NavigateToReviewScreen(
        MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(movieId, title)
      )
    }
  }

  private fun navigateToTrailerScreen(movieId: Int) {
    viewModelScope.launch {
      mState.value = MovieDetailsState.NavigateToTrailerScreen(movieId)
    }
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = MovieDetailsState.Idle
    }
  }
}
