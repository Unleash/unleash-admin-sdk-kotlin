package io.getunleash.admin.data

import java.time.ZonedDateTime

data class UnleashOverride(val contextName: String, val values: List<String>)
data class UnleashConstraint(val values: List<String>, val operator: String, val contextName: String)
data class UnleashVariant(val name: String, val weight: Int, val weightType: String, val stickiness: String, val payload: Map<String, String>, val overrides: List<UnleashOverride>)
data class UnleashStrategy(
    val name: String,
    val parameters: Map<String, String>,
    val constraints: List<UnleashConstraint>
)

data class UnleashEnvironment(
    val name: String,
    val enabled: Boolean,
    val type: String,
    val sortOrder: Int,
    val strategies: List<UnleashStrategy>
)

data class UnleashFeatureToggle(
    val environments: List<UnleashEnvironment> = emptyList(),
    val name: String,
    val description: String,
    val project: String,
    val stale: Boolean,
    val variants: UnleashVariant,
    val type: String,
    val archived: Boolean,
    val createdAt: ZonedDateTime? = null
)

data class UnleashFeatureTogglesResponse(val version: String, val features: List<UnleashFeatureToggle>)

data class CreateFeatureToggleRequest(val name: String, val description: String)
