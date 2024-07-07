package ren.pj.main.domain

import ren.pj.main.data.PurchaseEntity

// todo вынести в core
object PurchaseMapper {

    fun mapToPurchasesUi(purchases: List<PurchaseEntity>): List<PurchaseUi>{
        return purchases.map { mapToPurchaseUi(it) }
    }

    private fun mapToPurchaseUi(purchaseEntity: PurchaseEntity): PurchaseUi {
        return PurchaseUi(
            purchaseEntity.id.toInt(),
            purchaseEntity.purchaseName,
            "${purchaseEntity.amount.toBigDecimal()} ${purchaseEntity.currencyCode}"
        )
    }
}