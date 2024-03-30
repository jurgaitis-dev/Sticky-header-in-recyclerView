package com.z9.jur.jetpackcomposemvvm.di

import com.z9.stickyheaderinrecyclerview.data.repository.CountryRepositoryImpl
import com.z9.stickyheaderinrecyclerview.domain.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCountryRepository(
    ): CountryRepository {
        return CountryRepositoryImpl()
    }
}