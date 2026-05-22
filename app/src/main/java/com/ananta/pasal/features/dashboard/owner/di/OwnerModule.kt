package com.ananta.pasal.features.dashboard.owner.di

import com.ananta.pasal.features.dashboard.owner.data.repository.OwnerRepositoryImpl
import com.ananta.pasal.features.dashboard.owner.domain.repository.OwnerRepository
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