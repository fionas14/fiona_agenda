package com.fionasiregar0032.agenda.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.repository.AcaraRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(private val repository: AcaraRepository) : ViewModel() {
    val acara: StateFlow<List<Acara>> = repository.allAcara
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}