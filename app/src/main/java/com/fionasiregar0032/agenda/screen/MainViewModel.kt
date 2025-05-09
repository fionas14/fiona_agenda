package com.fionasiregar0032.agenda.screen

import androidx.lifecycle.ViewModel
import com.fionasiregar0032.agenda.model.Acara
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _acara = MutableStateFlow<List<Acara>>(emptyList())
    val acara: StateFlow<List<Acara>> = _acara
    init {
        val dummyAcara = listOf(
            Acara(1, "Seminar Magang FIT", "2025-05-10", "10:00", "13:00", "Aula Lantai 3", "Seminar magang bagi mahasiswa semester 4", "Seminar"),
        )
        _acara.value = dummyAcara
    }
}