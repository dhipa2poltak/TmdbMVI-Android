package com.dpfht.tmdbmvi.feature.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbmvi.R
import javax.inject.Inject
import javax.inject.Provider

class LoadingDialogProvider @Inject constructor(
  val context: Context
): Provider<AlertDialog> {

  override fun get(): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}