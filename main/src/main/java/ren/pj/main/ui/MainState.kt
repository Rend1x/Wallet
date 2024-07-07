package ren.pj.main.ui

import ren.pj.main.domain.PurchaseUi

data class MainState(
    val purchases: List<PurchaseUi> = emptyList()
)