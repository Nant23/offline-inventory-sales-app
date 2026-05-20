package com.ananta.pasal.source.local.dao

import androidx.room.*
import com.ananta.pasal.source.local.entity.OrderEntity
import com.ananta.pasal.source.local.entity.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    // owner sees all orders for their shop
    @Query("SELECT * FROM orders WHERE ownerId = :ownerId ORDER BY createdAt DESC")
    fun getOrdersForOwner(ownerId: String): Flow<List<OrderEntity>>

    // customer to see order history
    @Query("SELECT * FROM orders WHERE customerId = :customerId ORDER BY createdAt DESC")
    fun getOrdersForCustomer(customerId: String): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrderById(id: String): OrderEntity?

    @Query("SELECT * FROM orders WHERE syncStatus != :synced")
    suspend fun getPendingSync(synced: String = SyncStatus.SYNCED): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity)

    // update status
    @Query("UPDATE orders SET status = :status, syncStatus = :syncStatus, updatedAt = :now WHERE id = :id")
    suspend fun updateStatus(
        id: String,
        status: String,
        syncStatus: String = SyncStatus.PENDING,
        now: Long = System.currentTimeMillis()
    )

    @Query("UPDATE orders SET syncStatus = :synced WHERE id = :id")
    suspend fun markSynced(id: String, synced: String = SyncStatus.SYNCED)

}