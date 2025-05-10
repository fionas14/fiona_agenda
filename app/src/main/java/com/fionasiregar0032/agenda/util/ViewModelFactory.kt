package com.fionasiregar0032.agenda.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fionasiregar0032.agenda.database.AcaraDb
import com.fionasiregar0032.agenda.repository.AcaraRepository
import com.fionasiregar0032.agenda.viewmodel.AcaraFormViewModel
import com.fionasiregar0032.agenda.viewmodel.MainViewModel


class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val eventRepository by lazy {
            AcaraRepository(AcaraDb.getDatabase(application).acaraDao())
        }
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(eventRepository) as T
            }
            modelClass.isAssignableFrom(AcaraFormViewModel::class.java) -> {
                AcaraFormViewModel(eventRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}