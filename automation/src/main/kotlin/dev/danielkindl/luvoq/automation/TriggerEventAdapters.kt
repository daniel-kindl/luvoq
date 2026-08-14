package dev.danielkindl.luvoq.automation

import dev.danielkindl.luvoq.model.BatteryDirection
import dev.danielkindl.luvoq.model.ConnectionState

fun interface TriggerEventDispatcher {
    fun dispatch(event: TriggerEvent)
}

object PowerTriggerEventMapper {
    const val ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED"
    const val ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED"

    fun map(action: String?, occurredAtEpochMillis: Long): TriggerEvent? = when (action) {
        ACTION_POWER_CONNECTED -> TriggerEvent.Charging(
            state = ConnectionState.CONNECTED,
            occurredAtEpochMillis = occurredAtEpochMillis,
        )

        ACTION_POWER_DISCONNECTED -> TriggerEvent.Charging(
            state = ConnectionState.DISCONNECTED,
            occurredAtEpochMillis = occurredAtEpochMillis,
        )

        else -> null
    }
}

object TimeReachedTriggerEventMapper {
    fun map(hour: Int?, minute: Int?, occurredAtEpochMillis: Long): TriggerEvent? {
        if (hour == null || minute == null || hour !in 0..23 || minute !in 0..59) {
            return null
        }

        return TriggerEvent.TimeReached(
            hour = hour,
            minute = minute,
            occurredAtEpochMillis = occurredAtEpochMillis,
        )
    }
}

object BatteryTriggerEventMapper {
    fun map(
        previousLevelPercent: Int?,
        currentLevelPercent: Int?,
        thresholdPercent: Int,
        occurredAtEpochMillis: Long,
    ): TriggerEvent? {
        if (
            previousLevelPercent == null ||
            currentLevelPercent == null ||
            previousLevelPercent !in 0..100 ||
            currentLevelPercent !in 0..100 ||
            thresholdPercent !in 0..100
        ) {
            return null
        }

        return when {
            previousLevelPercent >= thresholdPercent && currentLevelPercent < thresholdPercent ->
                TriggerEvent.BatteryLevel(
                    direction = BatteryDirection.BELOW,
                    levelPercent = currentLevelPercent,
                    occurredAtEpochMillis = occurredAtEpochMillis,
                )

            previousLevelPercent <= thresholdPercent && currentLevelPercent > thresholdPercent ->
                TriggerEvent.BatteryLevel(
                    direction = BatteryDirection.ABOVE,
                    levelPercent = currentLevelPercent,
                    occurredAtEpochMillis = occurredAtEpochMillis,
                )

            else -> null
        }
    }
}
