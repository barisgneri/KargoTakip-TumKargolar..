package com.barisproduction.kargo.common.extensions


import androidx.compose.runtime.Composable
import kargotakiptumkargolar.composeapp.generated.resources.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun Long.toRelativeTime(): String {
    val instant = kotlin.time.Instant.fromEpochMilliseconds(this)
    val now = Clock.System.now()

    val duration = now - instant

    val minutes = duration.inWholeMinutes
    val hours = duration.inWholeHours
    val days = duration.inWholeDays

    return when {
        minutes < 1 -> stringResource(Res.string.just_now)
        minutes < 60 -> stringResource(Res.string.minutes_ago, minutes.toInt())
        hours < 24 -> stringResource(Res.string.hours_ago, hours.toInt())
        days <= 7 -> stringResource(Res.string.days_ago, days.toInt())
        else -> {
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val day = localDateTime.day
            val year = localDateTime.year

            val monthName = when (localDateTime.month.number) {
                1 -> stringResource(Res.string.month_jan)
                2 -> stringResource(Res.string.month_feb)
                3 -> stringResource(Res.string.month_mar)
                4 -> stringResource(Res.string.month_apr)
                5 -> stringResource(Res.string.month_may)
                6 -> stringResource(Res.string.month_jun)
                7 -> stringResource(Res.string.month_jul)
                8 -> stringResource(Res.string.month_aug)
                9 -> stringResource(Res.string.month_sep)
                10 -> stringResource(Res.string.month_oct)
                11 -> stringResource(Res.string.month_nov)
                12 -> stringResource(Res.string.month_dec)
                else -> ""
            }

            "$day $monthName $year"
        }
    }
}
