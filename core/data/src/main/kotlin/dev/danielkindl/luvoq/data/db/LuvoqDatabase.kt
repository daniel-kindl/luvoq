package dev.danielkindl.luvoq.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        RoutineEntity::class,
        TriggerEntity::class,
        ConditionEntity::class,
        ActionEntity::class,
        RoutineExecutionEntity::class,
        ActionExecutionEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class LuvoqDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    abstract fun triggerDao(): TriggerDao

    abstract fun conditionDao(): ConditionDao

    abstract fun actionDao(): ActionDao

    abstract fun executionDao(): ExecutionDao
}
