package com.fionasiregar0032.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.repository.AcaraRepository
import kotlinx.coroutines.flow.Flow
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

    fun saveAcara(acara: Acara, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                if (acara.id == 0L) { // Event baru
                    repository.insert(acara)
                } else { // Update event
                    repository.update(acara)
                }
                onResult(true)
            } catch (e: Exception) {
                // Handle error, misalnya log atau tampilkan pesan
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    fun deleteAcara(acara: Acara,onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteAcara(acara)
                onResult(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    fun getAcaraById(id: Long): Flow<Acara?> {
        return repository.getAcaraById(id)
    }
    fun prepareNewAcara() {
        _currentAcara.value = null
    }
}
