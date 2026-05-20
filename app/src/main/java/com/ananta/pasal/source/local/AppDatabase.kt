package com.ananta.pasal.source.local

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.ananta.pasal.source.local.dao.OrderDao
import com.ananta.pasal.source.local.dao.ProductDao
import com.ananta.pasal.source.local.entity.OrderEntity
import com.ananta.pasal.source.local.entity.ProductEntity


@Database(
    entities = [ProductEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}