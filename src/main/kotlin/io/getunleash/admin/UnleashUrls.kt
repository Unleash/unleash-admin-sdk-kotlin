package io.getunleash.admin

import okhttp3.HttpUrl

/**
 * Helper class holding builders for all admin URLs
 */
object UnleashUrls {
    /**
     * Yields the URL builder for the base admin URL.
     * @param baseUrl Where the server is located
     * @return The base admin URL. - UNLEASH_URL/api/admin
     */
    fun adminUrlBuilder(baseUrl: HttpUrl): HttpUrl.Builder {
        return if (baseUrl.toString().endsWith("/api")) {
            baseUrl.newBuilder().addPathSegment("admin")
        } else {
            baseUrl.newBuilder().addPathSegment("api").addPathSegment("admin")
        }
    }

    /**
     * Calls build on the [adminUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @return - The base admin URL
     */
    fun adminUrl(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).build()

    /**
     * Yields the URL builder for getting feature-types from the server
     * @param baseUrl Where the server is located
     * @return The feature type URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/feature-types
     */
    fun featureTypesUrlBuilder(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("feature-types")

    /**
     * Calls build on the [featureTypesUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @return - The featureTypes URL
     */
    fun featureTypesUrl(baseUrl: HttpUrl) = featureTypesUrlBuilder(baseUrl).build()

    /**
     * Yields the URL builder for getting tag-types from the server
     * @param baseUrl Where the server is located
     * @return The tag type URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/tag-types
     */
    fun tagTypesUrlBuilder(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("tag-types")

    /**
     * Calls build on the [tagTypesUrlBuilder] to get an HttpUrl instance
     * @param baseUrl Where the server is located
     * @return The tag-types URL
     */
    fun tagTypesUrl(baseUrl: HttpUrl) = tagTypesUrlBuilder(baseUrl).build()

    /**
     * Yields the URL builder for getting strategies from the server
     * @param baseUrl Where the server is located
     * @return The strategies URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/strategies
     */
    fun strategiesUrlBuilder(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("strategies")

    /**
     * Calls build on the [strategiesUrlBuilder] to get an HttpUrl instance
     * @param baseUrl Where the server is located
     * @return The strategies URL
     */
    fun strategiesUrl(baseUrl: HttpUrl) = strategiesUrlBuilder(baseUrl).build()


    /**
     * Yields the URL builder for getting projects from the server.
     * Most relevant for Enterprise installations. OSS installations will only have one project
     * @param baseUrl Where the server is located
     * @return The projects URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects
     */
    fun projectsUrlBuilder(baseUrl: HttpUrl): HttpUrl.Builder = adminUrlBuilder(baseUrl).addPathSegment("projects")

    /**
     * Calls build on [projectsUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     */
    fun projectsUrl(baseUrl: HttpUrl) = projectsUrlBuilder(baseUrl).build()

    /**
     * Yields the URL builder for a project.
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @return The project URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId
     */
    fun projectUrlBuilder(baseUrl: HttpUrl, projectId: String) = projectsUrlBuilder(baseUrl).addPathSegment(projectId)

    /**
     * Calls build on [projectUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @return The project URL
     */
    fun projectUrl(baseUrl: HttpUrl, projectId: String) = projectUrlBuilder(baseUrl, projectId).build()

    /**
     * Yields the URL builder for the project features list
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @return The features URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features
     */
    fun featuresUrlBuilder(baseUrl: HttpUrl, projectId: String) =
        projectUrlBuilder(baseUrl, projectId).addPathSegment("features")

    /**
     * Calls build on [featuresUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @return The features URL
     */
    fun featuresUrl(baseUrl: HttpUrl, projectId: String) = featuresUrlBuilder(baseUrl, projectId).build()

    /**
     * Yields the URL builder for a specific feature
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of the feature
     * @return The feature URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureId
     */
    fun featureUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featuresUrlBuilder(baseUrl, projectId).addPathSegment(featureName)

    /**
     * Calls build on [featureUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of the feature
     * @return The feature URL
     */
    fun featureUrl(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureUrlBuilder(baseUrl, projectId, featureName).build()

    /**
     * Yields the URL builder for the environments of a specific feature.
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @return The environments URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureId/environments
     */
    fun featureEnvironmentsUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureUrlBuilder(baseUrl, projectId, featureName).addPathSegment("environments")

    /**
     * Calls build on [featureEnvironmentsUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @return The environments URL
     */
    fun featureEnvironmentsUrl(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureEnvironmentsUrlBuilder(baseUrl, projectId, featureName).build()

    /**
     * Yields the URL builder for a specific environment of a specific feature.
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @return The environment URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureName/environments/$environmentName
     */
    fun featureEnvironmentUrlBuilder(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String
    ) = featureEnvironmentsUrlBuilder(baseUrl, projectId, featureName).addPathSegment(environmentName)

    /**
     * Calls build on [featureEnvironmentUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @return The environment URL
     */
    fun featureEnvironmentUrl(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String
    ) = featureEnvironmentUrlBuilder(baseUrl, projectId, featureName, environmentName).build()

    /**
     * Yields the URL builder for the strategies list for a specific environment for a specific feature
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @return The strategies URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureName/environments/$environmentName/strategies
     */
    fun featureStrategiesUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String, environmentName: String) =
        featureEnvironmentUrlBuilder(baseUrl, projectId, featureName, environmentName).addPathSegment("strategies")

    /**
     * Calls build on [featureStrategiesUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @return The strategies URL
     */
    fun featureStrategiesUrl(baseUrl: HttpUrl, projectId: String, featureName: String, environmentName: String) =
        featureStrategiesUrlBuilder(baseUrl, projectId, featureName, environmentName).build()

    /**
     * Yields the URL builder for a specific strategy for a specific environment for a specific feature
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @param strategyId The id of the strategy
     * @return The strategy URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureName/environments/$environmentName/strategies/$strategyId
     */
    fun featureStrategyUrlBuilder(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String,
        strategyId: String
    ) = featureStrategiesUrlBuilder(baseUrl, projectId, featureName, environmentName).addPathSegment(strategyId)

    /**
     * Calls build on [featureStrategyUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @param environmentName The name of the environment
     * @param strategyId The id of the strategy
     * @return The strategy URL
     */
    fun featureStrategyUrl(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String,
        strategyId: String
    ) = featureStrategyUrlBuilder(baseUrl, projectId, featureName, environmentName, strategyId).build()

    /**
     * Yields the URL builder for a features tags
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @return The tags URL builder which when built (using build()) will yield $UNLEASH_URL/api/admin/projects/$projectId/features/$featureName/tags
     */
    fun featureTagsUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureUrlBuilder(baseUrl, projectId, featureName).addPathSegment("tags")

    /**
     * Calls build on [featureTagsUrlBuilder] to get HttpUrl
     * @param baseUrl Where the server is located
     * @param projectId The id of the project
     * @param featureName The name of feature
     * @return The tags URL
     */
    fun featureTagsUrl(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureTagsUrlBuilder(baseUrl, projectId, featureName).build()
}
