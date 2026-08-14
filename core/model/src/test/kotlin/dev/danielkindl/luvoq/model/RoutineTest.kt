package dev.danielkindl.luvoq.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RoutineTest {
    private val trigger = Trigger.ChargingStarted
    private val action = Action.Vibrate(ActionId("action-1"))

    @Test
    fun routineRequiresAtLeastOneAction() {
        val exception = runCatching {
            Routine(
                id = RoutineId("routine-1"),
                name = "Charge reminder",
                enabled = false,
                trigger = trigger,
                actions = emptyList(),
                createdAtEpochMillis = 1L,
                updatedAtEpochMillis = 1L,
            )
        }.exceptionOrNull()

        assertTrue(exception is IllegalArgumentException)
    }

    @Test
    fun routineAllowsAndOrderedConditionsAndActions() {
        val routine = Routine(
            id = RoutineId("routine-1"),
            name = "Charge reminder",
            enabled = true,
            trigger = trigger,
            conditions = listOf(
                Condition.ChargingState(ConditionId("condition-1"), charging = true),
            ),
            actions = listOf(
                action,
                Action.SetBrightness(ActionId("action-2"), levelPercent = 40),
            ),
            createdAtEpochMillis = 1L,
            updatedAtEpochMillis = 2L,
        )

        assertEquals(1, routine.conditions.size)
        assertEquals(ActionId("action-2"), routine.actions[1].id)
    }

    @Test
    fun failuresAreCategorizedInsteadOfBoolean() {
        val outcome: ExecutionOutcome = ExecutionOutcome.Failure(
            FailureCategory.PERMISSION_MISSING,
        )

        assertTrue(outcome is ExecutionOutcome.Failure)
        assertEquals(
            FailureCategory.PERMISSION_MISSING,
            (outcome as ExecutionOutcome.Failure).category,
        )
    }
}
