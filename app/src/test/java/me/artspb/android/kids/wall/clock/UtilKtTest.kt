package me.artspb.android.kids.wall.clock

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilKtTest {

    companion object {
        private const val startHour = 8
        private const val endHour = 19
        private val start = combine(startHour, 0).toDate()
        private val end = combine(endHour, 0).toDate()
    }

    @Test
    fun toDay() {
        assertEquals(Day.NIGHT, combine(endHour + 1, 0).toDate().toDay(start, end))
        assertEquals(Day.NIGHT, combine(endHour + 1, 1).toDate().toDay(start, end))
        assertEquals(Day.NIGHT, combine(startHour - 2, 59).toDate().toDay(start, end))

        assertEquals(Day.DAWN, combine(startHour - 1, 0).toDate().toDay(start, end))
        assertEquals(Day.DAWN, combine(startHour - 1, 1).toDate().toDay(start, end))
        assertEquals(Day.DAWN, combine(startHour - 1, 59).toDate().toDay(start, end))

        assertEquals(Day.DAY, combine(startHour, 0).toDate().toDay(start, end))
        assertEquals(Day.DAY, combine(startHour, 1).toDate().toDay(start, end))
        assertEquals(Day.DAY, combine(endHour - 1, 59).toDate().toDay(start, end))

        assertEquals(Day.DUSK, combine(endHour, 0).toDate().toDay(start, end))
        assertEquals(Day.DUSK, combine(endHour, 1).toDate().toDay(start, end))
        assertEquals(Day.DUSK, combine(endHour, 59).toDate().toDay(start, end))
    }
}