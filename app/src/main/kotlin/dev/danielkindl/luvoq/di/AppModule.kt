package dev.danielkindl.luvoq.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.danielkindl.luvoq.automation.AutomationEngine
import dev.danielkindl.luvoq.automation.CapabilityRegistry
import dev.danielkindl.luvoq.automation.DefaultCapabilityRegistry
import dev.danielkindl.luvoq.automation.NoOpAutomationEngine
import dev.danielkindl.luvoq.data.analytics.Analytics
import dev.danielkindl.luvoq.data.analytics.NoOpAnalytics
import dev.danielkindl.luvoq.data.db.LuvoqDatabase
import dev.danielkindl.luvoq.data.entitlement.EntitlementRepository
import dev.danielkindl.luvoq.data.entitlement.LocalEntitlementRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LuvoqDatabase =
        Room.databaseBuilder(context, LuvoqDatabase::class.java, "luvoq.db").build()

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("luvoq_preferences") },
        )

    @Provides
    @Singleton
    fun provideEntitlementRepository(
        dataStore: DataStore<Preferences>,
    ): EntitlementRepository = LocalEntitlementRepository(dataStore)

    @Provides
    @Singleton
    fun provideAnalytics(): Analytics = NoOpAnalytics

    @Provides
    @Singleton
    fun provideAutomationEngine(): AutomationEngine = NoOpAutomationEngine()

    @Provides
    @Singleton
    fun provideCapabilityRegistry(): CapabilityRegistry = DefaultCapabilityRegistry()
}
