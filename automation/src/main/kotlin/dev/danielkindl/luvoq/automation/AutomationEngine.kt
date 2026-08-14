package dev.danielkindl.luvoq.automation

import dev.danielkindl.luvoq.model.CapabilityDescriptor
import dev.danielkindl.luvoq.model.CapabilityReliability
import dev.danielkindl.luvoq.model.CapabilityTypeId
import dev.danielkindl.luvoq.model.EntitlementTier
import dev.danielkindl.luvoq.model.RoutineExecution

interface AutomationEngine {
    suspend fun process(event: TriggerEvent): AutomationResult
}

data class AutomationResult(
    val eventType: CapabilityTypeId,
    val executions: List<RoutineExecution>,
)

class NoOpAutomationEngine : AutomationEngine {
    override suspend fun process(event: TriggerEvent): AutomationResult = AutomationResult(
        eventType = event.typeId,
        executions = emptyList(),
    )
}

interface CapabilityRegistry {
    fun all(): List<CapabilityDescriptor>

    fun find(typeId: CapabilityTypeId): CapabilityDescriptor? =
        all().firstOrNull { it.typeId == typeId }
}

class DefaultCapabilityRegistry : CapabilityRegistry {
    private val descriptors = listOf(
        descriptor("trigger.charging_started", "capability_charging_started", true),
        descriptor("trigger.charging_stopped", "capability_charging_stopped", true),
        descriptor("trigger.battery_below", "capability_battery_below", true),
        descriptor("trigger.battery_above", "capability_battery_above", true),
        descriptor("trigger.bluetooth_connected", "capability_bluetooth_connected", true),
        descriptor("trigger.bluetooth_disconnected", "capability_bluetooth_disconnected", true),
        descriptor("trigger.audio_device_connected", "capability_audio_device_connected", true),
        descriptor("trigger.audio_device_disconnected", "capability_audio_device_disconnected", true),
        descriptor("trigger.time_reached", "capability_time_reached", true),
        descriptor("condition.time_range", "capability_time_range", false),
        descriptor("condition.days_of_week", "capability_days_of_week", false),
        descriptor("condition.battery_level", "capability_battery_level", false),
        descriptor("condition.charging_state", "capability_charging_state", false),
        descriptor("condition.screen_state", "capability_screen_state", false),
        descriptor("action.set_media_volume", "capability_set_media_volume", false),
        descriptor("action.set_ringtone_volume", "capability_set_ringtone_volume", false),
        descriptor("action.set_alarm_volume", "capability_set_alarm_volume", false),
        descriptor("action.set_brightness", "capability_set_brightness", false),
        descriptor("action.open_app", "capability_open_app", false),
        descriptor("action.show_notification", "capability_show_notification", false),
        descriptor("action.vibrate", "capability_vibrate", false),
        descriptor("action.do_not_disturb", "capability_do_not_disturb", false),
    )

    override fun all(): List<CapabilityDescriptor> = descriptors

    private fun descriptor(typeId: String, key: String, isTrigger: Boolean) =
        CapabilityDescriptor(
            typeId = CapabilityTypeId(typeId),
            titleKey = "${key}_title",
            descriptionKey = "${key}_description",
            category = when {
                isTrigger -> dev.danielkindl.luvoq.model.CapabilityCategory.TRIGGER
                typeId.startsWith("condition.") -> dev.danielkindl.luvoq.model.CapabilityCategory.CONDITION
                else -> dev.danielkindl.luvoq.model.CapabilityCategory.ACTION
            },
            configurationSchemaVersion = 1,
            entitlement = EntitlementTier.FREE,
            reliability = CapabilityReliability.YELLOW,
        )
}
