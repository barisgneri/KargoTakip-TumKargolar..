package com.barisproduction.kargo.common.extensions


import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Long.toFormattedDate(): String {
    val instant = kotlin.time.Instant.fromEpochMilliseconds(this)
    val now = Clock.System.now()

    val duration = now - instant

    val minutes = duration.inWholeMinutes
    val hours = duration.inWholeHours
    val days = duration.inWholeDays

    return when {
        minutes < 1 -> "Az önce"
        minutes < 60 -> "${minutes}dk önce"
        hours < 24 -> "$hours saat önce"
        days <= 7 -> "$days gün önce"
        else -> {
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val day = localDateTime.day
            val year = localDateTime.year

            val monthName = when (localDateTime.month.number) {
                1 -> "Ocak"
                2 -> "Şubat"
                3 -> "Mart"
                4 -> "Nisan"
                5 -> "Mayıs"
                6 -> "Haziran"
                7 -> "Temmuz"
                8 -> "Ağustos"
                9 -> "Eylül"
                10 -> "Ekim"
                11 -> "Kasım"
                12 -> "Aralık"
                else -> ""
            }

            "$day $monthName $year"
        }
    }
}
