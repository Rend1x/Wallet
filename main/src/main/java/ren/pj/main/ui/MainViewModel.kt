package ren.pj.main.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ren.pj.main.data.PurchaseRepository
import ren.pj.main.mvi.MainActions
import ren.pj.main.mvi.MainSideEffect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(MainState())

    init {
        viewModelScope.launch {
            purchaseRepository.allPurchases.collect { purchases ->
                intent {
                    reduce {
                        state.copy(purchases = purchases)
                    }
                }
            }
        }
    }

    fun onEvent(event: MainActions) {
        when (event) {
            is MainActions.DeletePurchase -> deletePurchase(event.purchaseId)
        }
    }

    private fun deletePurchase(purchaseId: Long) {
        viewModelScope.launch {
            try {
                purchaseRepository.delete(purchaseId)
                updatePurchases()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
            }
        }
    }

    private fun updatePurchases() = intent {
        purchaseRepository.allPurchases.collect { purchases ->
            reduce {
                state.copy(purchases = purchases)
            }
        }
    }
}