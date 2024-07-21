package ren.pj.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ren.pj.core.data.PurchaseEntity
import ren.pj.create.data.CreateRepository
import ren.pj.notification.mapper.CategoryMapper.mapToCategorizePurchase
import ren.pj.notification.mapper.CurrencyMapper.mapSymbolToCode
import javax.inject.Inject

@AndroidEntryPoint
class PurchaseNotificationListenerService: NotificationListenerService() {

    @Inject
    lateinit var repository: CreateRepository

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val notification = sbn.notification
        val extras = notification.extras
        val title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text").toString()

        Log.d("NotificationListener", "Package: $packageName, Title: $title, Text: $text")

        val russianRegex = Regex("""Оплата: (\d{1,2},\d{1,2}) ([^\s]+) \(([^)]+)\)""")
        val englishRegex = Regex("""(\d{1,2},\d{1,2}) ([^\s]+) with ([^•]+)""")

        val transactionData = extractTransactionData(text, russianRegex) ?: extractTransactionData(text, englishRegex)

        transactionData?.let { savePurchaseToDatabase(it) }
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

    private fun savePurchaseToDatabase(purchase: PurchaseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(purchase)
            Log.d("NotificationListener", "Saved purchase to database: $purchase")
        }
    }
}