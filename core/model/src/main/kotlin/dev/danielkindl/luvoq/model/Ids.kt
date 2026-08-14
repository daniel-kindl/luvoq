package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class RoutineId(val value: String)

@Serializable
@JvmInline
value class ConditionId(val value: String)

@Serializable
@JvmInline
value class ActionId(val value: String)

@Serializable
@JvmInline
value class ExecutionId(val value: String)

@Serializable
@JvmInline
value class CapabilityTypeId(val value: String)

fun RoutineId.isValid(): Boolean = value.isNotBlank()

fun ConditionId.isValid(): Boolean = value.isNotBlank()

fun ActionId.isValid(): Boolean = value.isNotBlank()

fun ExecutionId.isValid(): Boolean = value.isNotBlank()
