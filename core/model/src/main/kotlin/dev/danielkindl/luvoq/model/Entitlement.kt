package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
data class Entitlement(val tier: EntitlementTier = EntitlementTier.FREE)
