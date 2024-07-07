package ren.pj.main.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    // todo вынести в core
    @Insert
    suspend fun insert(purchase: PurchaseEntity)

    @Delete
    suspend fun delete(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchases")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>
}