package com.fionasiregar0032.agenda.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acara_table")
data class Acara(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val acaraName: String,
    val acaraDate: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val description: String,
    val activityType: String,
    val isDeleted: Boolean = false

)

val acaraTypes = listOf("Seminar", "Workshop", "Pertemuan", "Lainnya")
