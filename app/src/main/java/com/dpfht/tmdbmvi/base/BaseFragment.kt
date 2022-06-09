package com.dpfht.tmdbmvi.base

import android.content.Context
import androidx.fragment.app.Fragment
import toothpick.config.Module
import toothpick.ktp.KTP

abstract class BaseFragment<VS>: Fragment() {

  override fun onAttach(context: Context) {
    super.onAttach(context)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope(this)
      .installModules(*getModules().toTypedArray())
      .inject(this)
  }

  protected abstract fun getModules(): ArrayList<Module>
  protected abstract fun render(state: VS)
}