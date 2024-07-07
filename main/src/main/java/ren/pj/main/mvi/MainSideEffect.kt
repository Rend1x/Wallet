package ren.pj.main.mvi

sealed class MainSideEffect {

    data object ShowToast : MainSideEffect()
}