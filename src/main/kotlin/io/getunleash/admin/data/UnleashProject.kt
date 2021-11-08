package io.getunleash.admin.data

data class UnleashProject(val id: String, val name: String? = null, val description: String? = null)
data class UnleashProjectResponse(val version: String, val projects: List<UnleashProject>)
