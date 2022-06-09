package com.dpfht.tmdbmvi.di.provider

import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class HttpLoggingInterceptorProvider @Inject constructor(

): Provider<HttpLoggingInterceptor> {
  override fun get(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }
}
