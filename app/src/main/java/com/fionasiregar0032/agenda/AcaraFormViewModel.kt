package com.fionasiregar0032.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.repository.AcaraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AcaraFormViewModel(private val repository: AcaraRepository) : ViewModel() {

    private val _currentAcara = MutableStateFlow<Acara?>(null)
    val currentAcara: StateFlow<Acara?> by lazy { _currentAcara.asStateFlow() }

    fun loadAcara(acaraId: Long) {
        viewModelScope.launch {
            repository.getAcaraById(acaraId).collect { acara ->
                _currentAcara.value = acara
            }
        }
    }

    fun saveAcara(acara: Acara) {
        viewModelScope.launch {
            if (acara.id == 0L) {
                repository.insert(acara)
            } else {
                repository.update(acara)
            }
        }
    }

    fun deleteAcara(acara: Acara) {
        viewModelScope.launch {
            repository.delete(acara)
        }
    }
}
