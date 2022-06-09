package com.dpfht.tmdbmvi.feature.movietrailer

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation
import com.dpfht.tmdbmvi.PlayerConfig
import com.dpfht.tmdbmvi.databinding.ActivityMovieTrailerBinding
import com.dpfht.tmdbmvi.feature.genre.GenreFragmentDirections
import com.dpfht.tmdbmvi.feature.movietrailer.MovieTrailerState.ErrorMessage
import com.dpfht.tmdbmvi.feature.movietrailer.MovieTrailerState.Idle
import com.dpfht.tmdbmvi.feature.movietrailer.MovieTrailerState.ViewTrailer
import com.dpfht.tmdbmvi.feature.movietrailer.di.MovieTrailerModule
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MovieTrailerActivity: YouTubeBaseActivity() {

  private lateinit var binding: ActivityMovieTrailerBinding
  private val viewModel by inject<MovieTrailerViewModel>()

  @Inject
  lateinit var scope: CoroutineScope

  override fun onCreate(savedInstanceState: Bundle?) {
    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope(this)
      .installModules(MovieTrailerModule(baseContext))
      .inject(this)

    super.onCreate(savedInstanceState)
    binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    scope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    if (intent.hasExtra("movie_id")) {
      val movieId = intent.getIntExtra("movie_id", -1)

      viewModel.setMovieId(movieId)

      scope.launch {
        viewModel.intents.send(MovieTrailerIntent.FetchTrailer)
      }
    }
  }

  private fun render(state: MovieTrailerState) {
    when (state) {
      is ViewTrailer -> {
        showTrailer(state.keyVideo)
      }
      is ErrorMessage -> {
        showErrorMessage(state.message)
      }
      Idle -> {

      }
    }
  }

  private fun showTrailer(keyVideo: String) {
    scope.launch(Dispatchers.Main) {
      binding.playerYoutube.initialize(
        PlayerConfig.API_KEY,
        object : YouTubePlayer.OnInitializedListener {

          override fun onInitializationSuccess(
            p0: YouTubePlayer.Provider?,
            youtubePlayer: YouTubePlayer?,
            p2: Boolean
          ) {
            youtubePlayer?.loadVideo(keyVideo)
            youtubePlayer?.play()
          }

          override fun onInitializationFailure(
            p0: YouTubePlayer.Provider?,
            p1: YouTubeInitializationResult?
          ) {
            Toast.makeText(
              baseContext,
              "error loading youtube video",
              Toast.LENGTH_SHORT
            ).show()
          }
        })
    }
  }

  private fun showErrorMessage(message: String) {
    if (message.isNotEmpty()) {
      val navDirections = GenreFragmentDirections.actionGenreFragmentToErrorDialog(message)
      Navigation.findNavController(binding.root).navigate(navDirections)
    }
  }

  override fun onPause() {
    super.onPause()
    scope.launch {
      viewModel.intents.send(MovieTrailerIntent.EnterIdleState)
    }
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
    super.onDestroy()
  }
}
