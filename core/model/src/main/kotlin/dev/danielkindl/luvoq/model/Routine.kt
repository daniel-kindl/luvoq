package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
data class Routine(
    val id: RoutineId,
    val name: String,
    val enabled: Boolean,
    val trigger: Trigger,
    val conditions: List<Condition> = emptyList(),
    val actions: List<Action>,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
) {
    init {
        require(id.isValid()) { "Routine ID must not be blank" }
        require(name.isNotBlank()) { "Routine name must not be blank" }
        require(actions.isNotEmpty()) { "A routine must contain at least one action" }
        require(updatedAtEpochMillis >= createdAtEpochMillis) {
            "Routine update time must not precede creation time"
        }
    }
}

@Serializable
data class RoutineExecution(
    val id: ExecutionId,
    val routineId: RoutineId,
    val startedAtEpochMillis: Long,
    val finishedAtEpochMillis: Long?,
    val outcome: ExecutionOutcome,
    val actionExecutions: List<ActionExecution>,
)

@Serializable
data class ActionExecution(
    val id: ExecutionId,
    val routineExecutionId: ExecutionId,
    val actionId: ActionId,
    val order: Int,
    val startedAtEpochMillis: Long,
    val finishedAtEpochMillis: Long?,
    val outcome: ExecutionOutcome,
) {
    init {
        require(order >= 0) { "Action order must be zero or greater" }
    }
}

@Serializable
sealed interface ExecutionOutcome {
    @Serializable
    data object Success : ExecutionOutcome

    @Serializable
    data class Failure(val category: FailureCategory) : ExecutionOutcome

    @Serializable
    data class Skipped(val reason: SkipReason) : ExecutionOutcome
}

@Serializable
enum class FailureCategory {
    PERMISSION_MISSING,
    CAPABILITY_UNAVAILABLE,
    INVALID_CONFIGURATION,
    TARGET_NOT_FOUND,
    ANDROID_RESTRICTION,
    TEMPORARY_FAILURE,
    UNKNOWN,
}

@Serializable
enum class SkipReason {
    ROUTINE_DISABLED,
    CONDITION_NOT_MET,
    CAPABILITY_UNAVAILABLE,
    DUPLICATE_TRIGGER,
    SAFETY_LIMIT_REACHED,
}
