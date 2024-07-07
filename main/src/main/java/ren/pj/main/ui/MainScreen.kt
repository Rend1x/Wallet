package ren.pj.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import ren.pj.main.data.PurchaseEntity
import ren.pj.main.domain.PurchaseUi
import ren.pj.main.mvi.MainActions

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val state = viewModel.container.stateFlow.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            val newPurchase = PurchaseEntity(
                purchaseName = "New Item",
                category = "Category",
                amount = 10.0
            )
            viewModel.onEvent(MainActions.AddPurchase(newPurchase))
        }) {
            Text(text = "Add Purchase")
        }

        LazyColumn {
            state.purchases
            items(state.purchases) { purchase ->
                PurchaseItem(purchase = purchase, onDelete = {
                    val newPurchase = PurchaseEntity(
                        purchaseName = "New Item",
                        category = "Category",
                        amount = 10.0
                    )
                    viewModel.onEvent(MainActions.DeletePurchase(newPurchase))
                })
            }
        }
    }
}

@Composable
fun PurchaseItem(purchase: PurchaseUi, onDelete: () -> Unit) {
    Column {
        Text(text = "Purchase: ${purchase.name}")
        Text(text = "Amount: ${purchase.price}")
        Button(onClick = onDelete) { Text(text = "Delete") }
    }
}