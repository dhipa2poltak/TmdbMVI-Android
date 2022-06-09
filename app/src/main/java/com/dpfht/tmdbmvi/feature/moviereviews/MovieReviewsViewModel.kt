package com.dpfht.tmdbmvi.feature.moviereviews

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbmvi.base.BaseViewModel
import com.dpfht.tmdbmvi.data.model.remote.Review
import com.dpfht.tmdbmvi.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.Success
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsIntent.EnterIdleState
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsIntent.FetchNextReview
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsIntent.FetchReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieReviewsViewModel @Inject constructor(
  private val getMovieReviewUseCase: GetMovieReviewUseCase,
  private val reviews: ArrayList<Review>
): BaseViewModel<MovieReviewsIntent, MovieReviewsState>() {

  override val mState = MutableStateFlow<MovieReviewsState>(MovieReviewsState.Idle)

  private var _movieId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchReview -> {
            start()
          }
          FetchNextReview -> {
            getMovieReviews()
          }
          EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  override fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  private fun getMovieReviews() {
    if (isEmptyNextResponse) return

    viewModelScope.launch(Dispatchers.Main) {
      mState.value = MovieReviewsState.IsLoading(true)
      mIsLoadingData = true

      when (val result = getMovieReviewUseCase(_movieId, page + 1)) {
        is Success -> {
          onSuccess(result.value.reviews, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        mState.value = MovieReviewsState.NotifyItemInserted(this.reviews.size - 1)
      }
    } else {
      isEmptyNextResponse = true
    }

    mState.value = MovieReviewsState.IsLoading(false)
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    mState.value = MovieReviewsState.IsLoading(false)
    mIsLoadingData = false
    mState.value = MovieReviewsState.ErrorMessage(message)
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = MovieReviewsState.Idle
    }
  }
}
