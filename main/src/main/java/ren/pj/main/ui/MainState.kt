package ren.pj.main.ui

import ren.pj.core.ui.PurchaseUi

data class MainState(
    val purchases: List<PurchaseUi> = emptyList()
)