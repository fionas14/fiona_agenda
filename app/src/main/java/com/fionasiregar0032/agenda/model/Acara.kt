package com.fionasiregar0032.agenda.model

data class Acara(
    val id: Long = 0L,
    val acaraName: String,
    val acaraDate: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val description: String,
    val activityType: String
)

val acaraTypes = listOf("Seminar", "Webinar", "Pelatihan", "Talkshow", "Kuliah Umum", "Bootcamp")
