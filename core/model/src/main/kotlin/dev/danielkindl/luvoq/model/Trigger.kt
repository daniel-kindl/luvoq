package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Trigger {
    val typeId: CapabilityTypeId

    @Serializable
    data object ChargingStarted : Trigger {
        override val typeId = CapabilityTypeId("trigger.charging_started")
    }

    @Serializable
    data object ChargingStopped : Trigger {
        override val typeId = CapabilityTypeId("trigger.charging_stopped")
    }

    @Serializable
    data class BatteryLevelReached(
        val direction: BatteryDirection,
        val thresholdPercent: Int,
    ) : Trigger {
        override val typeId = when (direction) {
            BatteryDirection.BELOW -> CapabilityTypeId("trigger.battery_below")
            BatteryDirection.ABOVE -> CapabilityTypeId("trigger.battery_above")
        }

        init {
            require(thresholdPercent in 0..100) { "Battery threshold must be between 0 and 100" }
        }
    }

    @Serializable
    data class BluetoothConnectionChanged(val state: ConnectionState) : Trigger {
        override val typeId = when (state) {
            ConnectionState.CONNECTED -> CapabilityTypeId("trigger.bluetooth_connected")
            ConnectionState.DISCONNECTED -> CapabilityTypeId("trigger.bluetooth_disconnected")
        }
    }

    @Serializable
    data class AudioDeviceConnectionChanged(val state: ConnectionState) : Trigger {
        override val typeId = when (state) {
            ConnectionState.CONNECTED -> CapabilityTypeId("trigger.audio_device_connected")
            ConnectionState.DISCONNECTED -> CapabilityTypeId("trigger.audio_device_disconnected")
        }
    }

    @Serializable
    data class TimeReached(val hour: Int, val minute: Int) : Trigger {
        override val typeId = CapabilityTypeId("trigger.time_reached")

        init {
            require(hour in 0..23) { "Hour must be between 0 and 23" }
            require(minute in 0..59) { "Minute must be between 0 and 59" }
        }
    }
}

@Serializable
enum class BatteryDirection {
    BELOW,
    ABOVE,
}

@Serializable
enum class ConnectionState {
    CONNECTED,
    DISCONNECTED,
}
