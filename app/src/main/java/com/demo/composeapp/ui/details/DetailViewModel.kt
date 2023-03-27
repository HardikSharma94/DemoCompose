package com.demo.composeapp.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val detailRepository: DetailRepository
) : ViewModel() {

  private val productIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

  val productDetailsFlow = productIdSharedFlow.flatMapLatest {
    detailRepository.getProductById(it)
  }

  init {
    Timber.d("init DetailViewModel")
  }

  fun loadProductById(id: Long) = productIdSharedFlow.tryEmit(id)
}
