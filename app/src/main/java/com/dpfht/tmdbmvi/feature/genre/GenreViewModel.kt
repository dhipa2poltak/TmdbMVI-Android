package com.dpfht.tmdbmvi.feature.genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbmvi.base.BaseViewModel
import com.dpfht.tmdbmvi.data.model.remote.Genre
import com.dpfht.tmdbmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbmvi.domain.usecase.UseCaseResultWrapper.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
  private val genres: ArrayList<Genre>
): BaseViewModel<GenreIntent, GenreState>() {

  override val mState = MutableStateFlow<GenreState>(GenreState.Idle)

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { genreIntent ->
        when (genreIntent) {
          GenreIntent.FetchGenre -> {
            start()
          }
          is GenreIntent.NavigateToNextScreen -> {
            navigateToNextScreen(genres[genreIntent.position].id, genres[genreIntent.position].name ?: "")
          }
          GenreIntent.EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    viewModelScope.launch {
      mState.value = GenreState.IsLoading(true)

      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccess(result.value.genres)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(genres: List<Genre>) {
    for (genre in genres) {
      this.genres.add(genre)
      mState.value = GenreState.NotifyItemInserted(this.genres.size - 1)
    }

    mState.value = GenreState.IsLoading(false)
  }

  private fun onError(message: String) {
    mState.value = GenreState.IsLoading(false)
    mState.value = GenreState.ErrorMessage(message)
  }

  private fun navigateToNextScreen(genreId: Int, genreName: String) {
    viewModelScope.launch {
      mState.value = GenreState.NavigateToNextScreen(
        GenreFragmentDirections.actionGenreFragmentToMoviesByGenreFragment(genreId, genreName)
      )
    }
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = GenreState.Idle
    }
  }
}
