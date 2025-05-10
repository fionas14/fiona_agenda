package com.fionasiregar0032.agenda
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fionasiregar0032.agenda.navigation.AppNavigation
import com.fionasiregar0032.agenda.ui.theme.AgendaTheme
import com.fionasiregar0032.agenda.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ViewModelFactory(application)

                setContent {
                    AgendaTheme {
                        AppNavigation()
                    }
                }
        }
    }

