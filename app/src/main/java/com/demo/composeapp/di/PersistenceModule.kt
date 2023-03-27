package com.demo.composeapp.di

import android.app.Application
import androidx.room.Room
import com.demo.composeapp.R
import com.demo.composeapp.persistence.AppDatabase
import com.demo.composeapp.persistence.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application): AppDatabase {
    return Room
      .databaseBuilder(
        application,
        AppDatabase::class.java,
        application.getString(R.string.database)
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  @Singleton
  fun providePosterDao(appDatabase: AppDatabase): ProductsDao {
    return appDatabase.posterDao()
  }
}
