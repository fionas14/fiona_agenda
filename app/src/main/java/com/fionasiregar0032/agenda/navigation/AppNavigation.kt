package com.fionasiregar0032.agenda.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fionasiregar0032.agenda.screen.AcaraFormScreen
import com.fionasiregar0032.agenda.screen.MainScreen
import com.fionasiregar0032.agenda.screen.RecycleBinScreen
import com.fionasiregar0032.agenda.util.ViewModelFactory
import com.fionasiregar0032.agenda.viewmodel.AcaraFormViewModel
import com.fionasiregar0032.agenda.viewmodel.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val application = LocalContext.current.applicationContext as Application
    val factory = ViewModelFactory(application)

    NavHost(navController = navController, startDestination = AppRoute.MainScreen.route) {
        composable(AppRoute.MainScreen.route) {
            val mainViewModelInstance: MainViewModel = viewModel(factory = factory)
            MainScreen(
                mainViewModel = mainViewModelInstance,
                onNavigateToForm = { eventId ->
                    navController.navigate(AppRoute.AcaraFormScreen.buildRoute(eventId))
                },
                onNavigateToRecycleBin = {
                    navController.navigate(AppRoute.RecycleBin.route)
                }
            )
        }

        composable(
            route = AppRoute.AcaraFormScreen.routeWithArgs,
            arguments = AppRoute.AcaraFormScreen.arguments
        ) { backStackEntry ->
            val acaraId = backStackEntry.arguments?.getLong(AppRoute.AcaraFormScreen.ACARA_ID_KEY)
            val acaraFormViewModelInstance: AcaraFormViewModel = viewModel(factory = factory)

            AcaraFormScreen(
                navController = navController,
                acaraId = if (acaraId == -1L) null else acaraId,
                viewModel = acaraFormViewModelInstance
            )
        }

        composable(AppRoute.RecycleBin.route) {
            val mainViewModelInstance: MainViewModel = viewModel(factory = factory)
            RecycleBinScreen(
                navController = navController,
                viewModel = mainViewModelInstance
            )

        }
    }
}