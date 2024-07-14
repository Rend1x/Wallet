package ren.pj.main.mvi

sealed class MainActions {

    data class DeletePurchase(val purchaseId: Long) : MainActions()
}