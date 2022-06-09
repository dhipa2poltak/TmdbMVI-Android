package com.dpfht.tmdbmvi.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<VI, VS>: ViewModel() {

  val intents: Channel<VI> = Channel(Channel.UNLIMITED)

  protected abstract val mState: MutableStateFlow<VS>
  val state: StateFlow<VS>
    get() = mState

  protected var mIsLoadingData = false

  fun isLoadingData() = mIsLoadingData

  protected abstract fun start()
}
