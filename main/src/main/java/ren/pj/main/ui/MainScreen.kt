package ren.pj.main.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ren.pj.core.ui.PurchaseUi
import ren.pj.main.mvi.MainActions

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val state = viewModel.container.stateFlow.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

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
            PurchaseItems(state.purchases) { id ->
                viewModel.onEvent(MainActions.DeletePurchase(id))
            }
        },
        bottomBar = { BottomNavigationBar() }
    )
}

@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(text = "Мои траты") }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurchaseItems(purchases: List<PurchaseUi>, onDelete: (Long) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(purchases, key = { it.id }) { purchase ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                        onDelete(purchase.id)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = { SwipeBackground(dismissState) },
                dismissContent = { PurchaseItem(purchase) }
            )
        }
    }
}

@Composable
fun PurchaseItem(purchase: PurchaseUi) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = purchase.name, fontSize = 20.sp, color = Color.Black)
                Text(text = purchase.category.name, fontSize = 14.sp, color = Color.Gray)
            }
            Text(text = "$${purchase.price}", fontSize = 20.sp, color = Color.Black)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) Color.Gray else Color.Red,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "Удалить",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Expenses") },
            label = { Text("Траты") },
            selected = true,
            onClick = { /* Handle navigation */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Reports") },
            label = { Text("Отчеты") },
            selected = false,
            onClick = { /* Handle navigation */ }
        )
    }
}