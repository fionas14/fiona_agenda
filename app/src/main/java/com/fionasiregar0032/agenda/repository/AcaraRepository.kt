package com.fionasiregar0032.agenda.repository

import com.fionasiregar0032.agenda.database.AcaraDao
import com.fionasiregar0032.agenda.model.Acara

class AcaraRepository(private val acaraDao: AcaraDao) {

    val getActiveAcara = acaraDao.getActiveAcara()
    val getDeletedAcara = acaraDao.getDeletedAcara()

    suspend fun insertAcara(acara: Acara) = acaraDao.insertAcara(acara)

    suspend fun deleteAcara(acara: Acara) = acaraDao.deleteAcara(acara)

    suspend fun updateAcara(acara: Acara) = acaraDao.updateAcara(acara)

    suspend fun softDeleteAcara(acara: Acara) {
        acaraDao.updateAcara(acara.copy(isDeleted = true))
    }

    suspend fun restoreAcara(acara: Acara) {
        acaraDao.updateAcara(acara.copy(isDeleted = false))
    }
    fun getAcaraById(id: Long) = acaraDao.getAcaraById(id)


}