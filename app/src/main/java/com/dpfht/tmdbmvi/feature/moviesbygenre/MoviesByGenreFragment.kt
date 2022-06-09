package com.dpfht.tmdbmvi.feature.moviesbygenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvi.base.BaseFragment
import com.dpfht.tmdbmvi.databinding.FragmentMoviesByGenreBinding
import com.dpfht.tmdbmvi.feature.moviesbygenre.MoviesByGenreState.ErrorMessage
import com.dpfht.tmdbmvi.feature.moviesbygenre.MoviesByGenreState.Idle
import com.dpfht.tmdbmvi.feature.moviesbygenre.MoviesByGenreState.IsLoading
import com.dpfht.tmdbmvi.feature.moviesbygenre.MoviesByGenreState.NavigateToNextScreen
import com.dpfht.tmdbmvi.feature.moviesbygenre.MoviesByGenreState.NotifyItemInserted
import com.dpfht.tmdbmvi.feature.moviesbygenre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbmvi.feature.moviesbygenre.adapter.MoviesByGenreAdapter.OnClickMovieListener
import com.dpfht.tmdbmvi.feature.moviesbygenre.di.MoviesByGenreModule
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MoviesByGenreFragment: BaseFragment<MoviesByGenreState>() {

  private lateinit var binding: FragmentMoviesByGenreBinding
  private val viewModel by inject<MoviesByGenreViewModel>()

  @Inject
  lateinit var adapter: MoviesByGenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MoviesByGenreModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvMoviesByGenre.layoutManager = layoutManager
    binding.rvMoviesByGenre.adapter = adapter

    adapter.onClickMovieListener = object : OnClickMovieListener {
      override fun onClickMovie(position: Int) {
        lifecycleScope.launch {
          viewModel.intents.send(MoviesByGenreIntent.NavigateToNextScreen(position))
        }
      }
    }

    binding.rvMoviesByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          lifecycleScope.launch {
            viewModel.intents.send(MoviesByGenreIntent.FetchNextMovie)
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

    val args = MoviesByGenreFragmentArgs.fromBundle(requireArguments())
    val genreId = args.genreId
    val genreName = args.genreName

    val title = "$genreName movies"
    binding.tvTitle.text = title

    viewModel.setGenreId(genreId)
    lifecycleScope.launch {
      viewModel.intents.send(MoviesByGenreIntent.FetchMovie)
    }
  }

  override fun render(state: MoviesByGenreState) {
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
      is NavigateToNextScreen -> {
        doNavigateToNextScreen(state.directions)
      }
      is Idle -> {
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
      val navDirections = MoviesByGenreFragmentDirections.actionMovieByGenreToErrorDialog(message)
      Navigation.findNavController(requireView()).navigate(navDirections)
    }
  }

  private fun doNavigateToNextScreen(directions: NavDirections) {
    try {
      Navigation.findNavController(requireView()).navigate(directions)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun onPause() {
    super.onPause()
    lifecycleScope.launch {
      viewModel.intents.send(MoviesByGenreIntent.EnterIdleState)
    }
  }
}
