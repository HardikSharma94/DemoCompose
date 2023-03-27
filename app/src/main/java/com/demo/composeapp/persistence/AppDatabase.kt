package com.demo.composeapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.composeapp.model.Products

@Database(entities = [Products::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun posterDao(): ProductsDao
}
