package com.ananta.pasal.source.local.dao

import androidx.room.*
import com.ananta.pasal.source.local.entity.ProductEntity
import com.ananta.pasal.source.local.entity.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // to display all the products for the shop owner
    @Query("SELECT * FROM products WHERE ownerId = :ownerId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun getAllProducts(ownerId: String): Flow<List<ProductEntity>>

    // fetch one product
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: String): ProductEntity?

    // get products pending sync
    @Query("SELECT * FROM products WHERE syncStatus != :synced")
    suspend fun getPendingSync(synced: String = SyncStatus.SYNCED): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    // marks isDeleted and sets syncStatus to DELETED
    @Query("UPDATE products SET isDeleted = 1, syncStatus = :deleted, updatedAt = :now WHERE id = :id")
    suspend fun softDelete(id: String, deleted: String = SyncStatus.DELETED, now: Long = System.currentTimeMillis())

    @Query("UPDATE products SET syncStatus = :synced WHERE id = :id")
    suspend fun markSynced(id: String, synced: String = SyncStatus.SYNCED)

    // to browse a specific shop's products
    @Query("SELECT * FROM products WHERE ownerId = :ownerId AND isDeleted = 0 AND stockQuantity > 0")
    fun getShopProducts(ownerId: String): Flow<List<ProductEntity>>

}