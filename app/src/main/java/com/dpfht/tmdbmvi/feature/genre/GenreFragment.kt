package com.dpfht.tmdbmvi.feature.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbmvi.base.BaseFragment
import com.dpfht.tmdbmvi.databinding.FragmentGenreBinding
import com.dpfht.tmdbmvi.feature.genre.GenreState.ErrorMessage
import com.dpfht.tmdbmvi.feature.genre.GenreState.Idle
import com.dpfht.tmdbmvi.feature.genre.GenreState.IsLoading
import com.dpfht.tmdbmvi.feature.genre.GenreState.NavigateToNextScreen
import com.dpfht.tmdbmvi.feature.genre.GenreState.NotifyItemInserted
import com.dpfht.tmdbmvi.feature.genre.adapter.GenreAdapter
import com.dpfht.tmdbmvi.feature.genre.di.GenreModule
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class GenreFragment: BaseFragment<GenreState>() {

  private lateinit var binding: FragmentGenreBinding
  private val viewModel by inject<GenreViewModel>()

  @Inject
  lateinit var adapter: GenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(GenreModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        lifecycleScope.launch {
          viewModel.intents.send(GenreIntent.NavigateToNextScreen(position))
        }
      }
    }

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    lifecycleScope.launch {
      viewModel.intents.send(GenreIntent.FetchGenre)
    }
  }

  override fun render(state: GenreState) {
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
      val navDirections = GenreFragmentDirections.actionGenreFragmentToErrorDialog(message)
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
      viewModel.intents.send(GenreIntent.EnterIdleState)
    }
  }
}
