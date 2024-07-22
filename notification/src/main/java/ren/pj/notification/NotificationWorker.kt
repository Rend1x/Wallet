package ren.pj.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ren.pj.core.data.PurchaseEntity
import ren.pj.create.data.CreateRepository
import ren.pj.notification.mapper.CategoryMapper.mapToCategorizePurchase
import ren.pj.notification.mapper.CurrencyMapper.mapSymbolToCode

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: CreateRepository
) : Worker(appContext, workerParams) {


    override fun doWork(): Result {
        val title = inputData.getString("title")
        val text = inputData.getString("text")

        if (title != null && text != null) {
            savePurchaseToDatabase(title, text)
        }

        return Result.success()
    }

    private fun savePurchaseToDatabase(title: String, text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val purchase = parseNotificationData(title, text)
            purchase?.let { repository.insert(it) }
        }
    }

    private fun parseNotificationData(title: String, text: String): PurchaseEntity? {
        val russianRegex = Regex("""Оплата: (\d{1,2},\d{1,2}) ([^\s]+) \(([^)]+)\)""")
        val englishRegex = Regex("""(\d{1,2},\d{1,2}) ([^\s]+) with ([^•]+)""")

        val transactionData = extractTransactionData(text, russianRegex) ?: extractTransactionData(text, englishRegex)

        return transactionData
    }

    private fun extractTransactionData(text: String, regex: Regex): PurchaseEntity? {
        val matchResult = regex.find(text)
        return if (matchResult != null) {
            val (amount, currencySymbol, place) = matchResult.destructured
            val currencyCode = mapSymbolToCode(currencySymbol) ?: "UNKNOWN"
            val category = mapToCategorizePurchase(place)
            PurchaseEntity(
                purchaseName = place,
                category = category,
                amount = amount.replace(',', '.').toDouble(),
                currencyCode = currencyCode,
                createdDate = System.currentTimeMillis()
            )
        } else {
            null
        }
    }
}