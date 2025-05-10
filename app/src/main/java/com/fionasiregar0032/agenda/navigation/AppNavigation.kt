package com.fionasiregar0032.agenda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fionasiregar0032.agenda.screen.AcaraFormScreen
import com.fionasiregar0032.agenda.screen.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onNavigateToForm = { acaraId ->
                    navController.navigate("form/${acaraId ?: "new"}")
                }
            )
        }
        composable("form/{acaraId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("acaraId")
            AcaraFormScreen(
                navController = navController,
                acaraId = id?.toLongOrNull()
            )
        }
    }
}
