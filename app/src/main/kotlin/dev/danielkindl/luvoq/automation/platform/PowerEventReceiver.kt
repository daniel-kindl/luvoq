package dev.danielkindl.luvoq.automation.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.danielkindl.luvoq.automation.PowerTriggerEventMapper
import dev.danielkindl.luvoq.automation.TriggerEventDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class PowerEventReceiver : BroadcastReceiver() {
    @Inject
    lateinit var eventDispatcher: TriggerEventDispatcher

    override fun onReceive(context: Context, intent: Intent) {
        PowerTriggerEventMapper
            .map(
                action = intent.action,
                occurredAtEpochMillis = System.currentTimeMillis(),
            )
            ?.let(eventDispatcher::dispatch)
    }
}
