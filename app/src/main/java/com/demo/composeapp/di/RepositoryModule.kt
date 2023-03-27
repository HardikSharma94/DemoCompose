package com.demo.composeapp.di

import com.demo.composeapp.network.ProductService
import com.demo.composeapp.persistence.ProductsDao
import com.demo.composeapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
    disneyService: ProductService,
    posterDao: ProductsDao
  ): MainRepository {
    return MainRepository(disneyService, posterDao)
  }
}
