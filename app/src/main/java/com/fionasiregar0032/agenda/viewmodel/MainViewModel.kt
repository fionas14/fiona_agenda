package com.fionasiregar0032.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.repository.AcaraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AcaraRepository) : ViewModel() {

    val activeAcara: StateFlow<List<Acara>> = repository.getActiveAcara
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val deletedAcara: StateFlow<List<Acara>> = repository.getDeletedAcara
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertAcara(acara: Acara) = viewModelScope.launch {
        repository.insertAcara(acara)
    }

    fun updateAcara(acara: Acara) = viewModelScope.launch {
        repository.updateAcara(acara)
    }

    fun deleteAcara(acara: Acara) = viewModelScope.launch {
        repository.deleteAcara(acara)
    }

    fun softDeleteAcara(acara: Acara) = viewModelScope.launch {
        repository.softDeleteAcara(acara)
    }

    fun restoreAcara(acara: Acara) = viewModelScope.launch {
        repository.restoreAcara(acara)
    }
}
