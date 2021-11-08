package io.getunleash.admin

import okhttp3.HttpUrl

object UnleashUrls {
    fun adminUrlBuilder(baseUrl: HttpUrl): HttpUrl.Builder {
        return if (baseUrl.toString().endsWith("/api")) {
            baseUrl.newBuilder().addPathSegment("admin")
        } else {
            baseUrl.newBuilder().addPathSegment("api").addPathSegment("admin")
        }
    }

    fun bootstrapUrl(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("ui-bootstrap")
    fun featureTypesUrl(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("feature-types")
    fun tagTypesUrl(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("tag-types")
    fun strategiesUrl(baseUrl: HttpUrl) = adminUrlBuilder(baseUrl).addPathSegment("strategies")
    fun projectsUrlBuilder(baseUrl: HttpUrl): HttpUrl.Builder = adminUrlBuilder(baseUrl).addPathSegment("projects")
    fun projectUrlBuilder(baseUrl: HttpUrl, projectId: String) = projectsUrlBuilder(baseUrl).addPathSegment(projectId)
    fun featuresUrlBuilder(baseUrl: HttpUrl, projectId: String) =
        projectUrlBuilder(baseUrl, projectId).addPathSegment("features")

    fun featureUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featuresUrlBuilder(baseUrl, projectId).addPathSegment(featureName)

    fun featureEnvironmentsUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureUrlBuilder(baseUrl, projectId, featureName).addPathSegment("environments")

    fun featureEnvironmentUrlBuilder(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String
    ) = featureEnvironmentsUrlBuilder(baseUrl, projectId, featureName).addPathSegment(environmentName)

    fun featureStrategiesUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String, environmentName: String) =
        featureEnvironmentUrlBuilder(baseUrl, projectId, featureName, environmentName).addPathSegment("strategies")

    fun featureStrategyUrlBuilder(
        baseUrl: HttpUrl,
        projectId: String,
        featureName: String,
        environmentName: String,
        strategyId: String
    ) = featureStrategiesUrlBuilder(baseUrl, projectId, featureName, environmentName).addPathSegment(strategyId)

    fun featureTagsUrlBuilder(baseUrl: HttpUrl, projectId: String, featureName: String) =
        featureUrlBuilder(baseUrl, projectId, featureName).addPathSegment("tags")
}
