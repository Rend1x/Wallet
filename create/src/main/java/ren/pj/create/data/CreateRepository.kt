package ren.pj.create.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ren.pj.core.data.PurchaseDao
import ren.pj.core.data.PurchaseEntity
import javax.inject.Inject

class CreateRepository @Inject constructor(
    private val purchaseDao: PurchaseDao
) {

    suspend fun insert(purchase: PurchaseEntity) = withContext(Dispatchers.IO) {
        try {
            purchaseDao.insert(purchase)
        } catch (e: Exception) {
            Log.e("CreateRepository", e.message.toString())
        }
    }
}