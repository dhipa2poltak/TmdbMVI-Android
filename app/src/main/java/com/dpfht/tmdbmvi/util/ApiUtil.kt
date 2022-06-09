package com.dpfht.tmdbmvi.util

import com.dpfht.tmdbmvi.data.api.ErrorResponse
import com.dpfht.tmdbmvi.data.api.ResultWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
  return withContext(dispatcher) {
    try {
      ResultWrapper.Success(apiCall.invoke())
    } catch (t: Throwable) {
      when (t) {
        is IOException -> ResultWrapper.NetworkError
        is HttpException -> {
          val code = t.code()
          val errorResponse = convertErrorBody(t)
          ResultWrapper.GenericError(code, errorResponse)
        }
        else -> {
          ResultWrapper.GenericError(null, null)
        }
      }
    }
  }
}

private fun convertErrorBody(t: HttpException): ErrorResponse? {
  return try {
    t.response()?.errorBody()?.source().let {
      val json = it?.readString(Charset.defaultCharset())
      val typeToken = object : TypeToken<ErrorResponse>() {}.type
      val errorResponse = Gson().fromJson<ErrorResponse>(json, typeToken)
      errorResponse
    }
  } catch (e: Exception) {
    null
  }
}
