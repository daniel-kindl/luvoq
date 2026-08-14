package dev.danielkindl.luvoq.automation

import dev.danielkindl.luvoq.model.BatteryDirection
import dev.danielkindl.luvoq.model.ConnectionState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TriggerEventAdaptersTest {
    @Test
    fun powerConnectedNormalizesToChargingStarted() {
        val event = PowerTriggerEventMapper.map(
            action = PowerTriggerEventMapper.ACTION_POWER_CONNECTED,
            occurredAtEpochMillis = 100L,
        )

        assertEquals(
            TriggerEvent.Charging(ConnectionState.CONNECTED, 100L),
            event,
        )
    }

    @Test
    fun unknownPowerActionIsIgnored() {
        assertNull(PowerTriggerEventMapper.map("unknown", 100L))
    }

    @Test
    fun timeEventRejectsMalformedAlarmExtras() {
        assertNull(TimeReachedTriggerEventMapper.map(24, 0, 100L))
        assertNull(TimeReachedTriggerEventMapper.map(12, 60, 100L))
        assertNull(TimeReachedTriggerEventMapper.map(null, 0, 100L))
    }

    @Test
    fun timeEventPreservesValidWallClockValues() {
        val event = TimeReachedTriggerEventMapper.map(7, 30, 100L)

        assertEquals(TriggerEvent.TimeReached(7, 30, 100L), event)
    }

    @Test
    fun batteryEventOnlyEmitsWhenCrossingTheThreshold() {
        val event = BatteryTriggerEventMapper.map(
            previousLevelPercent = 21,
            currentLevelPercent = 19,
            thresholdPercent = 20,
            occurredAtEpochMillis = 100L,
        )

        assertEquals(
            TriggerEvent.BatteryLevel(BatteryDirection.BELOW, 19, 100L),
            event,
        )
        assertNull(
            BatteryTriggerEventMapper.map(
                previousLevelPercent = 20,
                currentLevelPercent = 20,
                thresholdPercent = 20,
                occurredAtEpochMillis = 100L,
            ),
        )
    }
}
