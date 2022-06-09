package com.dpfht.tmdbmvi.di.provider

import com.dpfht.tmdbmvi.data.api.RestService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class RestServiceProvider @Inject constructor(
  private val retrofit: Retrofit
): Provider<RestService> {

  override fun get(): RestService {
    return retrofit.create(RestService::class.java)
  }
}