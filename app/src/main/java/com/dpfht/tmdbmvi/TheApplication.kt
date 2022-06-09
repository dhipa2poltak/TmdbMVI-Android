package com.dpfht.tmdbmvi

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDex
import com.dpfht.tmdbmvi.di.AppModule
import com.dpfht.tmdbmvi.di.NetworkModule
import toothpick.ktp.KTP

class TheApplication: Application() {

  companion object {
    lateinit var instance: TheApplication
  }

  override fun onCreate() {
    super.onCreate()
    instance = this

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
        Builder().detectAll()
          .penaltyLog()
          .build()
      )
      StrictMode.setVmPolicy(
        VmPolicy.Builder().detectAll()
          .penaltyLog()
          .build()
      )
    }

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .installModules(NetworkModule(), AppModule())
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}