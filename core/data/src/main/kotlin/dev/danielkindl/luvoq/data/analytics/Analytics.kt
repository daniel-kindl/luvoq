package dev.danielkindl.luvoq.data.analytics

interface Analytics {
    fun record(event: AnalyticsEvent)
}

sealed interface AnalyticsEvent {
    data class ScreenViewed(val screen: AnalyticsScreen) : AnalyticsEvent

    data object AppStarted : AnalyticsEvent

    data object RoutineCreated : AnalyticsEvent

    data object RoutineEnabled : AnalyticsEvent

    data object RoutineDisabled : AnalyticsEvent
}

enum class AnalyticsScreen {
    HOME,
    TEMPLATES,
    EDITOR,
    HISTORY,
    SETTINGS,
    PAYWALL,
}

object NoOpAnalytics : Analytics {
    override fun record(event: AnalyticsEvent) = Unit
}
