package ren.pj.wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ren.pj.create.ui.CreateScreen
import ren.pj.main.ui.MainScreen
import ren.pj.wallet.ui.theme.WalletTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletTheme {
                MyAppNavHost()
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("main") { MainScreen(navController) }
        composable("create") { CreateScreen(navController) }
    }
}