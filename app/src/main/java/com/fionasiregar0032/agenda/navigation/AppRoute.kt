package com.fionasiregar0032.agenda.navigation
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppRoute(val route: String) {
    object MainScreen : AppRoute("main_screen")
    object AcaraFormScreen : AppRoute("acara_form_screen") {
        const val ACARA_ID_KEY = "acaraId"

        val routeWithArgs = "acara_form_screen?$ACARA_ID_KEY={$ACARA_ID_KEY}"
        val arguments = listOf(
            navArgument(ACARA_ID_KEY) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
        fun buildRoute(acaraId: Long? = null): String {
            return if (acaraId != null && acaraId != -1L) {
                "acara_form_screen?$ACARA_ID_KEY=$acaraId"
            } else {
                "acara_form_screen"
            }
        }
    }
}
