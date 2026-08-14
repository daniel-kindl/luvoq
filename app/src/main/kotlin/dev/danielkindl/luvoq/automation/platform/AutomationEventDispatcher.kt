package dev.danielkindl.luvoq.automation.platform

import dev.danielkindl.luvoq.automation.AutomationEngine
import dev.danielkindl.luvoq.automation.TriggerEvent
import dev.danielkindl.luvoq.automation.TriggerEventDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AutomationEventDispatcher @Inject constructor(
    private val automationEngine: AutomationEngine,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : TriggerEventDispatcher {
    override fun dispatch(event: TriggerEvent) {
        applicationScope.launch {
            automationEngine.process(event)
        }
    }
}
