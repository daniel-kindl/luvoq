package dev.danielkindl.luvoq.automation

import dev.danielkindl.luvoq.model.BatteryDirection
import dev.danielkindl.luvoq.model.CapabilityTypeId
import dev.danielkindl.luvoq.model.ConnectionState
import kotlinx.serialization.Serializable

@Serializable
sealed interface TriggerEvent {
    val occurredAtEpochMillis: Long
    val typeId: CapabilityTypeId

    @Serializable
    data class Charging(
        val state: ConnectionState,
        override val occurredAtEpochMillis: Long,
    ) : TriggerEvent {
        override val typeId = when (state) {
            ConnectionState.CONNECTED -> CapabilityTypeId("trigger.charging_started")
            ConnectionState.DISCONNECTED -> CapabilityTypeId("trigger.charging_stopped")
        }
    }

    @Serializable
    data class BatteryLevel(
        val direction: BatteryDirection,
        val levelPercent: Int,
        override val occurredAtEpochMillis: Long,
    ) : TriggerEvent {
        override val typeId = when (direction) {
            BatteryDirection.BELOW -> CapabilityTypeId("trigger.battery_below")
            BatteryDirection.ABOVE -> CapabilityTypeId("trigger.battery_above")
        }

        init {
            require(levelPercent in 0..100) { "Battery level must be between 0 and 100" }
        }
    }

    @Serializable
    data class Bluetooth(
        val state: ConnectionState,
        override val occurredAtEpochMillis: Long,
    ) : TriggerEvent {
        override val typeId = when (state) {
            ConnectionState.CONNECTED -> CapabilityTypeId("trigger.bluetooth_connected")
            ConnectionState.DISCONNECTED -> CapabilityTypeId("trigger.bluetooth_disconnected")
        }
    }

    @Serializable
    data class AudioDevice(
        val state: ConnectionState,
        override val occurredAtEpochMillis: Long,
    ) : TriggerEvent {
        override val typeId = when (state) {
            ConnectionState.CONNECTED -> CapabilityTypeId("trigger.audio_device_connected")
            ConnectionState.DISCONNECTED -> CapabilityTypeId("trigger.audio_device_disconnected")
        }
    }

    @Serializable
    data class TimeReached(
        val hour: Int,
        val minute: Int,
        override val occurredAtEpochMillis: Long,
    ) : TriggerEvent {
        override val typeId = CapabilityTypeId("trigger.time_reached")

        init {
            require(hour in 0..23) { "Hour must be between 0 and 23" }
            require(minute in 0..59) { "Minute must be between 0 and 59" }
        }
    }
}
