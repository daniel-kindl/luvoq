package dev.danielkindl.luvoq.automation.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.danielkindl.luvoq.automation.TimeReachedTriggerEventMapper
import dev.danielkindl.luvoq.automation.TriggerEventDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class TimeReachedReceiver : BroadcastReceiver() {
    @Inject
    lateinit var eventDispatcher: TriggerEventDispatcher

    override fun onReceive(context: Context, intent: Intent) {
        TimeReachedTriggerEventMapper
            .map(
                hour = intent.getIntExtra(EXTRA_HOUR, -1),
                minute = intent.getIntExtra(EXTRA_MINUTE, -1),
                occurredAtEpochMillis = System.currentTimeMillis(),
            )
            ?.let(eventDispatcher::dispatch)
    }

    companion object {
        const val EXTRA_HOUR = "dev.danielkindl.luvoq.extra.TIME_HOUR"
        const val EXTRA_MINUTE = "dev.danielkindl.luvoq.extra.TIME_MINUTE"
    }
}
