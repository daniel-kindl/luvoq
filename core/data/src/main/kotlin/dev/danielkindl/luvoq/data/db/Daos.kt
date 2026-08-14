package dev.danielkindl.luvoq.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines ORDER BY updatedAtEpochMillis DESC")
    fun observeAll(): Flow<List<RoutineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(routine: RoutineEntity)

    @Query("DELETE FROM routines WHERE id = :id")
    suspend fun delete(id: String)
}

@Dao
interface TriggerDao {
    @Query("SELECT * FROM triggers WHERE routineId = :routineId")
    suspend fun findForRoutine(routineId: String): TriggerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(trigger: TriggerEntity)
}

@Dao
interface ConditionDao {
    @Query("SELECT * FROM conditions WHERE routineId = :routineId ORDER BY position")
    suspend fun findForRoutine(routineId: String): List<ConditionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(conditions: List<ConditionEntity>)
}

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions WHERE routineId = :routineId ORDER BY position")
    suspend fun findForRoutine(routineId: String): List<ActionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(actions: List<ActionEntity>)
}

@Dao
interface ExecutionDao {
    @Query("SELECT * FROM routine_executions ORDER BY startedAtEpochMillis DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<RoutineExecutionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineExecution(execution: RoutineExecutionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActionExecutions(executions: List<ActionExecutionEntity>)
}
