package ren.pj.create.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ren.pj.create.mvi.CreateActions
import ren.pj.create.mvi.CreateSideEffect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(navController: NavHostController, viewModel: CreateViewModel = hiltViewModel()) {
    val state by viewModel.container.stateFlow.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { event ->
            when (event) {
                CreateSideEffect.PurchaseAdded -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("create")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        },
        content = {
            CreateScreenContent(state, viewModel)
        }
    )
}

@Composable
fun CreateScreenContent(state: CreateViewState, viewModel: CreateViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = state.purchaseName,
            onValueChange = { viewModel.onEvent(CreateActions.EnteredName(it)) },
            label = {
                Text(text = "Название траты")
            },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.category,
            onValueChange = { viewModel.onEvent(CreateActions.EnteredCategory(it)) },
            label = { Text("Категория") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.amount,
            onValueChange = { viewModel.onEvent(CreateActions.EnteredAmount(it)) },
            label = { Text("Сумма") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.currencyCode,
            onValueChange = { viewModel.onEvent(CreateActions.EnteredCurrencyCode(it)) },
            label = { Text("Валюта") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { viewModel.onEvent(CreateActions.AddPurchase) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Сохранить")
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(text = "Добавить трату") }
    )
}