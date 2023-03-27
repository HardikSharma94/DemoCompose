package com.demo.composeapp.ui.details

import androidx.annotation.WorkerThread
import com.demo.composeapp.persistence.ProductsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
  private val productDao: ProductsDao
) {

  @WorkerThread
  fun getProductById(id: Long) = flow {
    val product = productDao.getProduct(id)
    emit(product)
  }.flowOn(Dispatchers.IO)
}
