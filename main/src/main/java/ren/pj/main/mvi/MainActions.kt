package ren.pj.main.mvi

import ren.pj.main.data.PurchaseEntity

sealed class MainActions {

    data class AddPurchase(val purchase: PurchaseEntity) : MainActions()

    data class DeletePurchase(val purchase: PurchaseEntity) : MainActions()
}