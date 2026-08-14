package dev.danielkindl.luvoq.automation

import dev.danielkindl.luvoq.model.ConnectionState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AutomationEngineTest {
    @Test
    fun noOpEngineDoesNotExecuteRoutines() = runTest {
        val event = TriggerEvent.Charging(
            state = ConnectionState.CONNECTED,
            occurredAtEpochMillis = 100L,
        )

        val result = NoOpAutomationEngine().process(event)

        assertEquals(event.typeId, result.eventType)
        assertTrue(result.executions.isEmpty())
    }

    @Test
    fun registryPreservesUnvalidatedCapabilityDefinitions() {
        val registry = DefaultCapabilityRegistry()

        assertTrue(registry.all().isNotEmpty())
        assertTrue(registry.all().all { it.reliability.name == "YELLOW" })
    }
}
