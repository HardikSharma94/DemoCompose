package com.demo.composeapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Products(
  @PrimaryKey val id: Long,
  val title: String,
  val price: String,
  val brand: String,
  val description: String,
  val stock: String,
  val thumbnail: String
) {

  companion object {

    fun mock() = Products(
      id = 0,
      title = "iPhone",
      price = "549",
      brand = "Apple",
      description = "An apple mobile which is nothing like apple",
      stock = "94",
      thumbnail = "https://i.dummyjson.com/data/products/1/thumbnail.jpg"
    )
  }
}
