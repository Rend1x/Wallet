package ren.pj.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Insert
    suspend fun insert(purchase: PurchaseEntity)

    @Query("DELETE FROM purchases WHERE id = :purchaseId")
    suspend fun delete(purchaseId: Long)

    @Query("SELECT * FROM purchases")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>
}