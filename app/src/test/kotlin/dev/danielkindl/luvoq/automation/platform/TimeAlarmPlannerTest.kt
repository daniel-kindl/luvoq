package dev.danielkindl.luvoq.automation.platform

import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeAlarmPlannerTest {
    private val zone = ZoneOffset.UTC

    @Test
    fun futureTimeIsScheduledToday() {
        val now = ZonedDateTime.of(2026, 8, 14, 8, 15, 30, 0, zone)

        assertEquals(
            ZonedDateTime.of(2026, 8, 14, 9, 30, 0, 0, zone),
            TimeAlarmPlanner.nextOccurrence(now, hour = 9, minute = 30),
        )
    }

    @Test
    fun currentOrPastTimeIsScheduledTomorrow() {
        val now = ZonedDateTime.of(2026, 8, 14, 9, 30, 0, 0, zone)

        assertEquals(
            ZonedDateTime.of(2026, 8, 15, 9, 30, 0, 0, zone),
            TimeAlarmPlanner.nextOccurrence(now, hour = 9, minute = 30),
        )
    }
}
