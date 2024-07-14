package ren.pj.create.ui

data class CreateViewState(
    val purchaseName: String = "",
    val category: String = "",
    val amount: String = "",
    val currencyCode: String = "EUR"
)