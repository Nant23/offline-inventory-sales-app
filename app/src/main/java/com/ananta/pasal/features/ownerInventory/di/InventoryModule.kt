package com.ananta.pasal.features.ownerInventory.di

import com.ananta.pasal.features.ownerInventory.data.InventoryRepositoryImpl
import com.ananta.pasal.features.ownerInventory.domain.InventoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InventoryModule {

    @Binds
    @Singleton
    abstract fun bindInventoryRepository(
        impl: InventoryRepositoryImpl
    ): InventoryRepository
}