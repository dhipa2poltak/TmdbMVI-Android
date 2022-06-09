package com.dpfht.tmdbmvi.di

import com.dpfht.tmdbmvi.data.repository.AppRepository
import com.dpfht.tmdbmvi.data.repository.AppRepositoryImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule: Module() {

  init {
    bind<AppRepository>().toClass(AppRepositoryImpl::class)
  }
}