package com.dpfht.tmdbmvi.feature.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.dpfht.tmdbmvi.R
import com.dpfht.tmdbmvi.base.BaseFragment
import com.dpfht.tmdbmvi.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.ErrorMessage
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.Idle
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.IsLoading
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.NavigateToReviewScreen
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.NavigateToTrailerScreen
import com.dpfht.tmdbmvi.feature.moviedetails.MovieDetailsState.ViewDetails
import com.dpfht.tmdbmvi.feature.moviedetails.di.MovieDetailsModule
import com.dpfht.tmdbmvi.feature.movietrailer.MovieTrailerActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MovieDetailsFragment: BaseFragment<MovieDetailsState>() {

  private lateinit var binding: FragmentMovieDetailsBinding
  private val viewModel by inject<MovieDetailsViewModel>()

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MovieDetailsModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.tvShowReview.setOnClickListener {
      onClickShowReview()
    }

    binding.tvShowTrailer.setOnClickListener {
      onClickShowTrailer()
    }

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    val args = MovieDetailsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId

    viewModel.setMovieId(movieId)

    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.FetchDetails)
    }
  }

  override fun render(state: MovieDetailsState) {
    when (state) {
      is IsLoading -> {
        showLoading(state.value)
      }
      is ErrorMessage -> {
        showErrorMessage(state.message)
      }
      is ViewDetails -> {
        showDetails(state.title, state.overview, state.imageUrl)
      }
      is NavigateToReviewScreen -> {
        navigateToReviewScreen(state.directions)
      }
      is NavigateToTrailerScreen -> {
        navigateToTrailerScreen(state.movieId)
      }
      Idle -> {

      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
      loadingDialog.show()
    } else {
      loadingDialog.dismiss()
    }
  }

  private fun showErrorMessage(message: String) {
    val navDirections = MovieDetailsFragmentDirections.actionMovieDetailsToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun showDetails(title: String, overview: String, imageUrl: String) {
    binding.tvTitleMovie.text = title
    binding.tvDescMovie.text = overview

    Picasso.get().load(imageUrl)
      .error(android.R.drawable.ic_menu_close_clear_cancel)
      .placeholder(R.drawable.loading)
      .into(binding.ivImageMovie)
  }

  private fun onClickShowReview() {
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.NavigateToReviewScreen)
    }
  }

  private fun onClickShowTrailer() {
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.NavigateToTrailerScreen)
    }
  }

  private fun navigateToReviewScreen(directions: NavDirections) {
    try {
      Navigation.findNavController(requireView()).navigate(directions)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun navigateToTrailerScreen(movieId: Int) {
    val itn = Intent(requireContext(), MovieTrailerActivity::class.java)
    itn.putExtra("movie_id", movieId)
    requireActivity().startActivity(itn)
  }

  override fun onPause() {
    super.onPause()
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.EnterIdleState)
    }
  }
}
