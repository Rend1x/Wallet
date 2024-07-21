package ren.pj.create.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ren.pj.core.data.Category
import ren.pj.core.data.PurchaseEntity
import ren.pj.create.data.CreateRepository
import ren.pj.create.mvi.CreateActions
import ren.pj.create.mvi.CreateSideEffect
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createRepository: CreateRepository
) : ViewModel(), ContainerHost<CreateViewState, CreateSideEffect> {

    override val container: Container<CreateViewState, CreateSideEffect> =
        container(CreateViewState())

    fun onEvent(event: CreateActions) {
        when (event) {
            is CreateActions.AddPurchase -> addPurchase()
            is CreateActions.EnteredAmount -> {
                intent { reduce { state.copy(amount = event.value) } }
            }

            is CreateActions.EnteredCategory -> {
                intent { reduce { state.copy(category = event.value) } }
            }

            is CreateActions.EnteredCurrencyCode -> {
                intent { reduce { state.copy(currencyCode = event.value) } }
            }

            is CreateActions.EnteredName -> {
                intent { reduce { state.copy(purchaseName = event.value) } }
            }
        }
    }

    private fun addPurchase() = intent {
        viewModelScope.launch {
            try {
                val purchase = PurchaseEntity(
                    purchaseName = state.purchaseName,
                    category = Category.valueOf(state.category),
                    amount = state.amount.toDoubleOrNull() ?: 0.0,
                    currencyCode = state.currencyCode
                )
                createRepository.insert(purchase)
                postSideEffect(CreateSideEffect.PurchaseAdded)
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
            }
        }
    }
}