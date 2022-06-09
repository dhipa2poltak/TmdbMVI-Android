package com.dpfht.tmdbmvi.feature.moviesbygenre.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbmvi.data.model.remote.Genre
import com.dpfht.tmdbmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbmvi.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbmvi.feature.common.LoadingDialogProvider
import com.dpfht.tmdbmvi.feature.genre.adapter.GenreAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MoviesByGenreModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieByGenreUseCase>().toClass<GetMovieByGenreUseCaseImpl>()
    bind<ArrayList<Genre>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
