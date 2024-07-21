package ren.pj.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val purchaseName: String,
    val category: Category,
    val amount: Double,
    val currencyCode: String,
    val createdDate: Long = System.currentTimeMillis()
)

enum class Category {
    MEDICINE, FOOD, FUEL, SPORT, EVENTS, TRANSPORT, ENTERTAINMENT, UTILITIES, SHOPPING, TRAVEL, OTHER
}