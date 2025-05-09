package com.fionasiregar0032.agenda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fionasiregar0032.agenda.screen.EventFormScreen
import com.fionasiregar0032.agenda.screen.MainScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppRoute.MainScreen.route) {
        composable(AppRoute.MainScreen.route) {
            MainScreen (
                onNavigateToForm = { acaraId ->
                    if (acaraId == null) {
                        navController.navigate(AppRoute.AcaraFormScreen.routeWithoutArgs)
                    } else {
                        navController.navigate("${AppRoute.AcaraFormScreen.routeWithoutArgs}?${AppRoute.AcaraFormScreen.ACARA_ID_KEY}=$acaraId")
                    }
                }
            )
        }
        composable(
            route = AppRoute.AcaraFormScreen.routeWithArgs,
            arguments = AppRoute.AcaraFormScreen.arguments
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getLong(AppRoute.AcaraFormScreen.ACARA_ID_KEY) ?: -1L
            EventFormScreen(
                navController = navController,
                acaraId = if (eventId == -1L) null else eventId
            )
        }
    }
}