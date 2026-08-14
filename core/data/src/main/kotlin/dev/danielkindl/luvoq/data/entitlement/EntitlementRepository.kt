package dev.danielkindl.luvoq.data.entitlement

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.danielkindl.luvoq.model.Entitlement
import dev.danielkindl.luvoq.model.EntitlementTier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface EntitlementRepository {
    val entitlement: Flow<Entitlement>
}

class LocalEntitlementRepository(
    private val dataStore: DataStore<Preferences>,
) : EntitlementRepository {
    private val tierKey = stringPreferencesKey("entitlement_tier")

    override val entitlement: Flow<Entitlement> = dataStore.data.map { preferences ->
        Entitlement(
            tier = preferences[tierKey]
                ?.let { value -> runCatching { EntitlementTier.valueOf(value) }.getOrNull() }
                ?: EntitlementTier.FREE,
        )
    }

    suspend fun setLocalTierForTests(tier: EntitlementTier) {
        dataStore.edit { preferences -> preferences[tierKey] = tier.name }
    }
}
