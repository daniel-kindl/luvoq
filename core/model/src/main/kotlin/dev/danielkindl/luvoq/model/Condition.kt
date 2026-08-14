package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Condition {
    val id: ConditionId
    val typeId: CapabilityTypeId

    @Serializable
    data class TimeRange(
        override val id: ConditionId,
        val startMinuteOfDay: Int,
        val endMinuteOfDay: Int,
    ) : Condition {
        override val typeId = CapabilityTypeId("condition.time_range")

        init {
            require(id.isValid()) { "Condition ID must not be blank" }
            require(startMinuteOfDay in 0..1439) { "Start time must be a valid minute of day" }
            require(endMinuteOfDay in 0..1439) { "End time must be a valid minute of day" }
        }
    }

    @Serializable
    data class DaysOfWeek(
        override val id: ConditionId,
        val days: Set<DayOfWeek>,
    ) : Condition {
        override val typeId = CapabilityTypeId("condition.days_of_week")

        init {
            require(id.isValid()) { "Condition ID must not be blank" }
            require(days.isNotEmpty()) { "At least one day must be selected" }
        }
    }

    @Serializable
    data class BatteryLevel(
        override val id: ConditionId,
        val comparison: Comparison,
        val thresholdPercent: Int,
    ) : Condition {
        override val typeId = CapabilityTypeId("condition.battery_level")

        init {
            require(id.isValid()) { "Condition ID must not be blank" }
            require(thresholdPercent in 0..100) { "Battery threshold must be between 0 and 100" }
        }
    }

    @Serializable
    data class ChargingState(
        override val id: ConditionId,
        val charging: Boolean,
    ) : Condition {
        override val typeId = CapabilityTypeId("condition.charging_state")

        init {
            require(id.isValid()) { "Condition ID must not be blank" }
        }
    }

    @Serializable
    data class ScreenState(
        override val id: ConditionId,
        val screenOn: Boolean,
    ) : Condition {
        override val typeId = CapabilityTypeId("condition.screen_state")

        init {
            require(id.isValid()) { "Condition ID must not be blank" }
        }
    }
}

@Serializable
enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
}

@Serializable
enum class Comparison {
    EQUAL,
    BELOW,
    ABOVE,
}
