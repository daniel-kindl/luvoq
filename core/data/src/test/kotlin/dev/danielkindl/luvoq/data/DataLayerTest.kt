package dev.danielkindl.luvoq.data

import dev.danielkindl.luvoq.data.analytics.AnalyticsEvent
import dev.danielkindl.luvoq.data.analytics.NoOpAnalytics
import org.junit.Test

class DataLayerTest {
    @Test
    fun noOpAnalyticsAcceptsTypedEventsWithoutSideEffects() {
        NoOpAnalytics.record(AnalyticsEvent.AppStarted)
    }
}
