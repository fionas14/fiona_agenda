package com.fionasiregar0032.agenda.repository

import com.fionasiregar0032.agenda.database.AcaraDao
import com.fionasiregar0032.agenda.model.Acara
import kotlinx.coroutines.flow.Flow
class AcaraRepository(private val acaraDao: AcaraDao) {
    val allAcara: Flow<List<Acara>> = acaraDao.getAllAcara()

    fun getAcaraById(id: Long): Flow<Acara?> {
        return acaraDao.getAcaraById(id)
    }

    suspend fun insert(acara: Acara) {
        acaraDao.insertAcara(acara)
    }

    suspend fun update(acara: Acara) {
        acaraDao.updateAcara(acara)
    }

    suspend fun deleteAcara(acara: Acara) {
        acaraDao.deleteAcara(acara)
    }
}
