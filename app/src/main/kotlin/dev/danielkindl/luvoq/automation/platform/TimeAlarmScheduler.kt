package dev.danielkindl.luvoq.automation.platform

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.danielkindl.luvoq.automation.platform.TimeReachedReceiver.Companion.EXTRA_HOUR
import dev.danielkindl.luvoq.automation.platform.TimeReachedReceiver.Companion.EXTRA_MINUTE
import java.time.Clock
import java.time.ZonedDateTime
import javax.inject.Inject

data class TimeAlarmSchedule(
    val triggerAtEpochMillis: Long,
    val exact: Boolean,
    val exactAlarmAccessRequired: Boolean,
)

class TimeAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(
        hour: Int,
        minute: Int,
        clock: Clock = Clock.systemDefaultZone(),
    ): TimeAlarmSchedule {
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }

        val now = ZonedDateTime.now(clock)
        val nextOccurrence = TimeAlarmPlanner.nextOccurrence(now, hour, minute)
        val pendingIntent = pendingIntent(hour, minute)
        val exactAlarmAccessRequired = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()

        if (exactAlarmAccessRequired) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextOccurrence.toInstant().toEpochMilli(),
                pendingIntent,
            )
        } else {
            setExactAlarm(nextOccurrence.toInstant().toEpochMilli(), pendingIntent)
        }

        return TimeAlarmSchedule(
            triggerAtEpochMillis = nextOccurrence.toInstant().toEpochMilli(),
            exact = !exactAlarmAccessRequired,
            exactAlarmAccessRequired = exactAlarmAccessRequired,
        )
    }

    fun cancel(hour: Int, minute: Int) {
        alarmManager.cancel(pendingIntent(hour, minute))
    }

    private fun pendingIntent(hour: Int, minute: Int): PendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE_BASE + (hour * 60) + minute,
        Intent().setClass(context, TimeReachedReceiver::class.java).apply {
            putExtra(EXTRA_HOUR, hour)
            putExtra(EXTRA_MINUTE, minute)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setExactAlarm(triggerAtEpochMillis: Long, pendingIntent: PendingIntent) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtEpochMillis,
            pendingIntent,
        )
    }

    private companion object {
        const val REQUEST_CODE_BASE = 1001
    }
}
