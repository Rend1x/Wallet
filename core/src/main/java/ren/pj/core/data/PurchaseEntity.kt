package ren.pj.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val purchaseName: String,
    val category: String,
    val amount: Double,
    val currencyCode: String = "EUR"
)
