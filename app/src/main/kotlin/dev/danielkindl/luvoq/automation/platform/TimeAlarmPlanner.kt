package dev.danielkindl.luvoq.automation.platform

import java.time.ZonedDateTime

internal object TimeAlarmPlanner {
    fun nextOccurrence(now: ZonedDateTime, hour: Int, minute: Int): ZonedDateTime {
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }

        val candidate = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        return if (candidate.isAfter(now)) candidate else candidate.plusDays(1)
    }
}
