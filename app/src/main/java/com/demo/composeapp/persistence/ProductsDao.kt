package com.demo.composeapp.persistence

import androidx.room.*
import com.demo.composeapp.model.Products

@Dao
interface ProductsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProductList(posters: List<Products>)

  @Query("SELECT * FROM Products WHERE id = :id_")
  suspend fun getProduct(id_: Long): Products?

  @Query("SELECT * FROM Products")
  suspend fun getProductList(): List<Products>
}
