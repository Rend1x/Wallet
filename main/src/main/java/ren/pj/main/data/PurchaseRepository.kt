package ren.pj.main.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ren.pj.main.domain.PurchaseMapper.mapToPurchasesUi
import ren.pj.main.domain.PurchaseUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseRepository @Inject constructor(
    private val purchaseDao: PurchaseDao
) {

    val allPurchases: Flow<List<PurchaseUi>> = purchaseDao.getAllPurchases().map(::mapToPurchasesUi)

    // todo вынести в core
    suspend fun insert(purchase: PurchaseEntity) = withContext(Dispatchers.IO) {
        try {
            purchaseDao.insert(purchase)
        } catch (e: Exception) {
            Log.e("PurchaseRepository", e.message.toString())
        }
    }

    suspend fun delete(purchase: PurchaseEntity) = withContext(Dispatchers.IO) {
        try {
            purchaseDao.delete(purchase)
        } catch (e: Exception) {
            Log.e("PurchaseRepository", e.message.toString())
        }
    }
}