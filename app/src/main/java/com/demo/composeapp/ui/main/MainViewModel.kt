package com.demo.composeapp.ui.main

import android.database.Observable
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.demo.composeapp.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.ObservableProperty

@HiltViewModel
class MainViewModel @Inject constructor(
  mainRepository: MainRepository
) : ViewModel() {

  val posterList: Flow<List<Products>> =
    mainRepository.loadProductPosters(
      onStart = { _isLoading.value = true },
      onCompletion = { _isLoading.value = false
                     _isError.value = false},
      onError = {
        Timber.d(it)
      _isError.value = true
      }
    )


  fun changeValue(value:Boolean){
    _isRefresh.value = value
  }

  private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
  val isLoading: State<Boolean> get() = _isLoading

  private val _isError: MutableState<Boolean> = mutableStateOf(false)
  val isError: State<Boolean> get() = _isError

  val _isRefresh: MutableState<Boolean> = mutableStateOf(false)
  val isRefresh: State<Boolean> get() = _isRefresh



  init {
    Timber.d("injection MainViewModel")
  }

}
