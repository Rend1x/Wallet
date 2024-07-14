package ren.pj.main.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ren.pj.core.data.PurchaseDao
import ren.pj.core.ui.PurchaseMapper.mapToPurchasesUi
import ren.pj.core.ui.PurchaseUi
import javax.inject.Inject
import javax.inject.Singleton

class PurchaseRepository @Inject constructor(
    private val purchaseDao: PurchaseDao
) {

    val allPurchases: Flow<List<PurchaseUi>> = purchaseDao.getAllPurchases().map(::mapToPurchasesUi)

    suspend fun delete(purchaseId: Long) = withContext(Dispatchers.IO) {
        try {
            purchaseDao.delete(purchaseId)
        } catch (e: Exception) {
            Log.e("PurchaseRepository", e.message.toString())
        }
    }
}