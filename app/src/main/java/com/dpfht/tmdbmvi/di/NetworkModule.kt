package com.dpfht.tmdbmvi.di

import com.dpfht.tmdbmvi.data.api.RestService
import com.dpfht.tmdbmvi.di.provider.CertificatePinnerProvider
import com.dpfht.tmdbmvi.di.provider.HttpLoggingInterceptorProvider
import com.dpfht.tmdbmvi.di.provider.OkHttpClientProvider
import com.dpfht.tmdbmvi.di.provider.RestServiceProvider
import com.dpfht.tmdbmvi.di.provider.RetrofitProvider
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NetworkModule: Module() {

  init {
    bind<CertificatePinner>().toProvider(CertificatePinnerProvider::class)
    bind<HttpLoggingInterceptor>().toProvider(HttpLoggingInterceptorProvider::class)
    bind<OkHttpClient>().toProvider(OkHttpClientProvider::class)
    bind<Retrofit>().toProvider(RetrofitProvider::class)
    bind<RestService>().toProvider(RestServiceProvider::class)
  }
}
