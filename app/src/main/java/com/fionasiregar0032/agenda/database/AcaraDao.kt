package com.fionasiregar0032.agenda.database
import androidx.room.*
import com.fionasiregar0032.agenda.model.Acara
import kotlinx.coroutines.flow.Flow

@Dao
interface AcaraDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcara(acara: Acara)

    @Delete
    suspend fun deleteAcara(acara: Acara)

    @Update
    suspend fun updateAcara(acara: Acara)

    @Query("SELECT * FROM acara_table ORDER BY acaraDate DESC, startTime DESC")
    fun getAllAcara(): Flow<List<Acara>>

    @Query("SELECT * FROM acara_table WHERE id = :acaraId")
    fun getAcaraById(acaraId: Long): Flow<Acara?>
}