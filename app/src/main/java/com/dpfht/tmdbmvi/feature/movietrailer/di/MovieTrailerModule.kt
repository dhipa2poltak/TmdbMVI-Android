package com.dpfht.tmdbmvi.feature.movietrailer.di

import android.content.Context
import com.dpfht.tmdbmvi.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbmvi.domain.usecase.GetMovieTrailerUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieTrailerModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieTrailerUseCase>().toClass<GetMovieTrailerUseCaseImpl>()
    bind<Job>().toInstance(Job())
    bind<CoroutineScope>().toProvider(CoroutineScopeProvider::class)
  }
}
