package com.dpfht.tmdbmvi.feature.genre.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbmvi.data.model.remote.Genre
import com.dpfht.tmdbmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbmvi.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbmvi.feature.common.LoadingDialogProvider
import com.dpfht.tmdbmvi.feature.genre.adapter.GenreAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class GenreModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieGenreUseCase>().toClass<GetMovieGenreUseCaseImpl>()
    bind<ArrayList<Genre>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
