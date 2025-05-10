package com.fionasiregar0032.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.repository.AcaraRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AcaraFormViewModel(
    private val repository: AcaraRepository
) : ViewModel() {

    private val _currentAcara = MutableStateFlow<Acara?>(null)
    val currentAcara: StateFlow<Acara?> = _currentAcara.asStateFlow()

    fun prepareNewAcara() {
        _currentAcara.value = null
    }

    fun loadAcara(acaraId: Long) {
        viewModelScope.launch {
            repository.getAcaraById(acaraId).collect { acara ->
                _currentAcara.value = acara
            }
        }
    }

    fun saveAcara(acara: Acara, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                if (acara.id == 0L) {
                    repository.insertAcara(acara)
                } else {
                    repository.updateAcara(acara)
                }
                onResult(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    fun deleteAcara(acara: Acara, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateAcara(acara)
                onResult(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    fun getAcaraById(id: Long): Flow<Acara?> = repository.getAcaraById(id)
}
