package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
data class CapabilityDescriptor(
    val typeId: CapabilityTypeId,
    val titleKey: String,
    val descriptionKey: String,
    val category: CapabilityCategory,
    val configurationSchemaVersion: Int,
    val requiredAccess: Set<CapabilityAccess> = emptySet(),
    val availability: AndroidAvailability = AndroidAvailability(),
    val entitlement: EntitlementTier = EntitlementTier.FREE,
    val reliability: CapabilityReliability,
)

@Serializable
enum class CapabilityCategory {
    TRIGGER,
    CONDITION,
    ACTION,
}

@Serializable
enum class CapabilityReliability {
    GREEN,
    YELLOW,
    RED,
}

@Serializable
enum class CapabilityAccess {
    NONE,
    NOTIFICATION_PERMISSION,
    DO_NOT_DISTURB_ACCESS,
    SYSTEM_SETTINGS_ACCESS,
}

@Serializable
data class AndroidAvailability(
    val minimumApi: Int = 26,
    val maximumApi: Int? = null,
)

@Serializable
enum class EntitlementTier {
    FREE,
    PRO,
}
