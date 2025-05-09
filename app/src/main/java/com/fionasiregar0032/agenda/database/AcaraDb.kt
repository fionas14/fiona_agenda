package com.fionasiregar0032.agenda.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fionasiregar0032.agenda.model.Acara

@Database(entities = [Acara::class], version = 1, exportSchema = false)
abstract class AcaraDb: RoomDatabase() {
    abstract fun AcaraDao(): AcaraDao

    companion object {
        @Volatile
        private var INSTANCE: AcaraDb? = null

        fun getDatabase(context: Context): AcaraDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AcaraDb::class.java,
                    "acara_acara_Db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}