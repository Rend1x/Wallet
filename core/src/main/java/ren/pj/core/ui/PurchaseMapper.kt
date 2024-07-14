package ren.pj.core.ui

import ren.pj.core.data.PurchaseEntity

object PurchaseMapper {

    fun mapToPurchasesUi(purchases: List<PurchaseEntity>): List<PurchaseUi> {
        return purchases.map { mapToPurchaseUi(it) }
    }

    private fun mapToPurchaseUi(purchaseEntity: PurchaseEntity): PurchaseUi {
        return PurchaseUi(
            purchaseEntity.id,
            purchaseEntity.purchaseName,
            purchaseEntity.category,
            "${purchaseEntity.amount.toBigDecimal()} ${purchaseEntity.currencyCode}"
        )
    }
}