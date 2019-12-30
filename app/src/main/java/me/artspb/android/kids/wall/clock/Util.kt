package me.artspb.android.kids.wall.clock

import java.util.*

internal fun combine(hours: Int, minutes: Int) = hours * 60 + minutes

internal fun Int.split(): Pair<Int, Int> = Pair(this / 60, this % 60)

internal fun Int.toDate(): Calendar {
    val (hours, minutes) = split()
    val date = Calendar.getInstance()
    date.set(Calendar.HOUR_OF_DAY, hours)
    date.set(Calendar.MINUTE, minutes)
    date.set(Calendar.SECOND, 0)
    return date
}

internal enum class Day { NIGHT, DAWN, DAY, DUSK }

internal fun Calendar.toDay(start: Calendar, end: Calendar): Day {
    if (between(start, end)) {
        return Day.DAY
    }
    val startMinusHour = start.clone() as Calendar
    startMinusHour.add(Calendar.HOUR, -1)
    if (between(startMinusHour, start)) {
        return Day.DAWN
    }
    val endPlusHour = end.clone() as Calendar
    endPlusHour.add(Calendar.HOUR, 1)
    if (between(end, endPlusHour)) {
        return Day.DUSK
    }
    return Day.NIGHT
}

private fun Calendar.between(start: Calendar, end: Calendar) = after(start) && before(end)
