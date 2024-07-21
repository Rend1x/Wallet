package ren.pj.core.ui

import ren.pj.core.data.Category

data class PurchaseUi(
    val id: Long,
    val name: String,
    val category: Category,
    val price: String
)