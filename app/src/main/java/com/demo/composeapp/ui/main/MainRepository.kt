package com.demo.composeapp.ui.main

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.demo.composeapp.model.Products
import com.demo.composeapp.network.ProductService
import com.demo.composeapp.persistence.ProductsDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
  private val productService: ProductService,
  private val productDao: ProductsDao
) {

  init {
    Timber.d("Injection MainRepository")
  }

  @WorkerThread
  fun loadProductPosters(
    onStart: () -> Unit,
    onCompletion: () -> Unit,
    onError: (String) -> Unit
  ) = flow {
    val posters: List<Products> = productDao.getProductList()
    if (posters.isEmpty()) {
      // request API network call asynchronously.
      productService.fetchProductPosterList()
        // handle the case when the API request gets a success response.
        .suspendOnSuccess {
          productDao.insertProductList(data.products)
          emit(data.products)
        }
        // handle the case when the API request is fails.
        // e.g. internal server error.
        .onFailure {
          onError(message())
        }
    } else {
      emit(posters)
    }
  }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}
