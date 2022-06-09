package com.dpfht.tmdbmvi.di.provider

import com.dpfht.tmdbmvi.BuildConfig
import com.dpfht.tmdbmvi.data.api.AuthInterceptor
import com.dpfht.tmdbmvi.data.api.UnsafeOkHttpClient
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor(
  private val certificatePinner: CertificatePinner
): Provider<OkHttpClient> {

  override fun get(): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient()
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(AuthInterceptor())

    return httpClientBuilder.build()
  }
}