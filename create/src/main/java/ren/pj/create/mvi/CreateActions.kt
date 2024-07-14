package ren.pj.create.mvi

sealed class CreateActions {
    data class EnteredName(val value: String) : CreateActions()
    data class EnteredCategory(val value: String) : CreateActions()
    data class EnteredAmount(val value: String) : CreateActions()
    data class EnteredCurrencyCode(val value: String) : CreateActions()
    object AddPurchase : CreateActions()
}