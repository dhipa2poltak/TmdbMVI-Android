package com.dpfht.tmdbmvi.feature.moviereviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvi.base.BaseFragment
import com.dpfht.tmdbmvi.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsState.ErrorMessage
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsState.Idle
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsState.IsLoading
import com.dpfht.tmdbmvi.feature.moviereviews.MovieReviewsState.NotifyItemInserted
import com.dpfht.tmdbmvi.feature.moviereviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbmvi.feature.moviereviews.di.MovieReviewsModule
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MovieReviewsFragment: BaseFragment<MovieReviewsState>() {

  private lateinit var binding: FragmentMovieReviewsBinding
  private val viewModel by inject<MovieReviewsViewModel>()

  @Inject
  lateinit var adapter: MovieReviewsAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MovieReviewsModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieReviewsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = adapter

    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          lifecycleScope.launch {
            viewModel.intents.send(MovieReviewsIntent.FetchNextReview)
          }
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    val args = MovieReviewsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId
    val movieTitle = args.movieTitle

    binding.tvMovieName.text = movieTitle

    viewModel.setMovieId(movieId)

    lifecycleScope.launch {
      viewModel.intents.send(MovieReviewsIntent.FetchReview)
    }
  }

  override fun render(state: MovieReviewsState) {
    when (state) {
      is NotifyItemInserted -> {
        doNotifyItemInserted(state.value)
      }
      is IsLoading -> {
        showLoading(state.value)
      }
      is ErrorMessage -> {
        showErrorMessage(state.message)
      }
      Idle -> {

      }
    }
  }

  private fun doNotifyItemInserted(position: Int) {
    if (position > 0) {
      adapter.notifyItemInserted(position)
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
    if (message.isNotEmpty()) {
      val navDirections = MovieReviewsFragmentDirections.actionMovieReviewsToErrorDialog(message)
      Navigation.findNavController(requireView()).navigate(navDirections)
    }
  }

  override fun onPause() {
    super.onPause()
    lifecycleScope.launch {
      viewModel.intents.send(MovieReviewsIntent.EnterIdleState)
    }
  }
}
