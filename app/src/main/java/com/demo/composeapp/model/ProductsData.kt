package com.demo.composeapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Entity
@Immutable
data class ProductsData(
   val products:List<Products>
)
