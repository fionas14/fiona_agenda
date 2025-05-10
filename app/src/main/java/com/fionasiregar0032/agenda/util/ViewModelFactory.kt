package com.fionasiregar0032.agenda.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fionasiregar0032.agenda.AcaraFormViewModel
import com.fionasiregar0032.agenda.database.AcaraDb
import com.fionasiregar0032.agenda.repository.AcaraRepository
import com.fionasiregar0032.agenda.screen.MainViewModel


class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val acaraRepository = AcaraRepository(AcaraDb.getDatabase(application).acaraDao())

        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(acaraRepository) as T
            }
            modelClass.isAssignableFrom(AcaraFormViewModel::class.java) -> {
                AcaraFormViewModel(acaraRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
