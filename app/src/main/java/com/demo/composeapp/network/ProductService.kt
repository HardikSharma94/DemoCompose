package com.demo.composeapp.network

import com.demo.composeapp.model.ProductsData
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ProductService {

  @GET("products")
  suspend fun fetchProductPosterList(): ApiResponse<ProductsData>
}
