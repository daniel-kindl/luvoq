package dev.danielkindl.luvoq.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "routines")
data class RoutineEntity(
    @androidx.room.PrimaryKey val id: String,
    val name: String,
    val enabled: Boolean,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
)

@Entity(
    tableName = "triggers",
    primaryKeys = ["routineId"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("routineId")],
)
data class TriggerEntity(
    val routineId: String,
    val type: String,
    val schemaVersion: Int,
    val configJson: String,
)

@Entity(
    tableName = "conditions",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("routineId"), Index(value = ["routineId", "position"], unique = true)],
)
data class ConditionEntity(
    val id: String,
    val routineId: String,
    val position: Int,
    val type: String,
    val schemaVersion: Int,
    val configJson: String,
)

@Entity(
    tableName = "actions",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("routineId"), Index(value = ["routineId", "position"], unique = true)],
)
data class ActionEntity(
    val id: String,
    val routineId: String,
    val position: Int,
    val type: String,
    val schemaVersion: Int,
    val configJson: String,
)

@Entity(
    tableName = "routine_executions",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("routineId"), Index("startedAtEpochMillis")],
)
data class RoutineExecutionEntity(
    val id: String,
    val routineId: String?,
    val startedAtEpochMillis: Long,
    val finishedAtEpochMillis: Long?,
    val outcome: String,
    val failureCategory: String?,
    val skipReason: String?,
)

@Entity(
    tableName = "action_executions",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineExecutionEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineExecutionId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("routineExecutionId"), Index("actionId")],
)
data class ActionExecutionEntity(
    val id: String,
    val routineExecutionId: String,
    val actionId: String,
    val position: Int,
    val startedAtEpochMillis: Long,
    val finishedAtEpochMillis: Long?,
    val outcome: String,
    val failureCategory: String?,
    val skipReason: String?,
)
