package com.fionasiregar0032.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.fionasiregar0032.agenda.navigation.AppNavigation

import com.fionasiregar0032.agenda.ui.theme.AgendaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                AgendaTheme {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController)
                }
        }
    }
}



