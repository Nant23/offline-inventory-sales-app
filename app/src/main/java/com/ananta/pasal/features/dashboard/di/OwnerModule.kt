package com.ananta.pasal.features.dashboard.di

import com.ananta.pasal.features.dashboard.domain.data.repository.OwnerRepositoryImpl
import com.ananta.pasal.features.dashboard.domain.repository.OwnerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OwnerModule {
    @Binds
    @Singleton
    abstract fun bindOwnerRepository(impl: OwnerRepositoryImpl): OwnerRepository
}