package io.getunleash.admin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.getunleash.admin.data.CreateFeatureToggleRequest
import io.getunleash.admin.data.UnleashFeatureToggle
import io.getunleash.admin.data.UnleashFeatureTogglesResponse
import io.getunleash.admin.data.UnleashProject
import io.getunleash.admin.data.UnleashProjectResponse
import io.getunleash.admin.data.serializers.GsonZonedDateTimeSerializer
import io.getunleash.admin.exceptions.UnleashIllegalContentTypeException
import io.getunleash.admin.exceptions.UnleashUniqueViolationException
import io.getunleash.admin.exceptions.UnleashWrongApiKeyException
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.Duration
import java.time.ZonedDateTime

/**
 * Main entry point for Unleash Admin operations
 *
 */
class UnleashAdminClient(val serverUrl: HttpUrl, val apiKey: String, val client: OkHttpClient? = null) {
    constructor(serverAddress: String, apiKey: String, client: OkHttpClient? = null) : this(
        serverAddress.toHttpUrl(),
        apiKey,
        client
    )

    private val internalClient: OkHttpClient = client ?: OkHttpClient.Builder().addInterceptor { chain ->
        val authedRequest = chain.request().newBuilder()
            .addHeader("Authorization", apiKey)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Unleash-Admin-Client", "unleash-admin-client")
            .addHeader(
                "X-Unleash-Admin-Client-Version",
                this@UnleashAdminClient.javaClass.`package`.implementationVersion ?: "SNAPSHOT"
            )
            .build()
        chain.proceed(authedRequest)
    }
        .readTimeout(Duration.ofSeconds(5))
        .connectTimeout(Duration.ofSeconds(2))
        .build()

    val gson: Gson =
        GsonBuilder().registerTypeAdapter(ZonedDateTime::class.java, GsonZonedDateTimeSerializer()).create()


    /**
     * Gets the projects from the configured server
     * @return the server's available projects
     */
    @Throws(UnleashIllegalContentTypeException::class)
    fun getProjects(): List<UnleashProject> {
        val request = Request.Builder().url(UnleashUrls.projectsUrl(serverUrl)).get().build()
        internalClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                val unleashProjectResponse = gson.fromJson(response.body?.string(), UnleashProjectResponse::class.java)
                unleashProjectResponse.projects
            } else {
                when (response.code) {
                    415 -> {
                        val contentTypeHeader =
                            response.request.header("Content-Type") ?: "No Content-Type header was set"
                        throw UnleashIllegalContentTypeException(
                            message = "Illegal Content type caused a 415",
                            contentTypeHeader = contentTypeHeader
                        )
                    }
                    else -> emptyList()
                }
            }
        }
    }

    /**
     * Gets the features for a specific project from the configured server
     * @param projectId the project to get data for
     * @return the project's available feature toggles
     */
    fun getFeatures(projectId: String): List<UnleashFeatureToggle> {
        val request = Request.Builder().url(UnleashUrls.featuresUrl(serverUrl, projectId)).get().build()
        internalClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                val unleashFeaturesResponse =
                    gson.fromJson(response.body?.string(), UnleashFeatureTogglesResponse::class.java)
                unleashFeaturesResponse.features
            } else {
                when (response.code) {
                    415 -> {
                        val contentTypeHeader =
                            response.request.header("Content-Type") ?: "No Content-Type header was set"
                        throw UnleashIllegalContentTypeException(
                            message = "Illegal Content type caused a 415 error",
                            contentTypeHeader = contentTypeHeader
                        )
                    }
                    401 -> {
                        throw UnleashWrongApiKeyException(message = "Api key was rejected and caused a 401 error")
                    }
                    else -> emptyList()
                }
            }
        }
    }

    /**
     * Gets a specific feature by name
     * @param projectId the project the feature belongs to
     * @param featureName the name of the feature
     * @return the feature toggle
     */
    fun getFeature(projectId: String, featureName: String): UnleashFeatureToggle? {
        val request = Request.Builder()
            .url(UnleashUrls.featureUrl(baseUrl = serverUrl, projectId = projectId, featureName = featureName))
            .get()
            .build()
        internalClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                val unleashFeatureResponse =
                    gson.fromJson(response.body?.string(), UnleashFeatureToggle::class.java)
                unleashFeatureResponse
            } else {
                when (response.code) {
                    415 -> {
                        val contentTypeHeader =
                            response.request.header("Content-Type") ?: "No Content-Type header was set"
                        throw UnleashIllegalContentTypeException(
                            message = "Illegal Content type caused a 415 error",
                            contentTypeHeader = contentTypeHeader
                        )
                    }
                    401 -> {
                        throw UnleashWrongApiKeyException(message = "Api key was rejected and caused a 401 error")
                    }
                    else -> null
                }
            }
        }
    }

    /**
     * Creates a feature belonging to project
     * @param projectId the project the feature should belong to
     * @param featureName the name of the feature - must be unique on server
     * @param featureDescription optional feature description
     * @return the created feature toggle
     */
    fun createFeature(
        projectId: String,
        featureName: String,
        featureDescription: String? = null
    ): UnleashFeatureToggle? {
        val body = gson.toJson(CreateFeatureToggleRequest(name = featureName, description = featureDescription ?: ""))
        val request = Request.Builder()
            .url(UnleashUrls.featuresUrl(baseUrl = serverUrl, projectId = projectId))
            .post(body.toRequestBody())
            .build()
        internalClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                gson.fromJson(response.body?.string(), UnleashFeatureToggle::class.java)
            } else {
                when (response.code) {
                    409 -> throw UnleashUniqueViolationException(message = "$featureName was already in use")
                    415 -> {
                        val contentTypeHeader =
                            response.request.header("Content-Type") ?: "No Content-Type header was set"
                        throw UnleashIllegalContentTypeException(
                            message = "Illegal Content type caused a 415 error",
                            contentTypeHeader = contentTypeHeader
                        )
                    }
                    else -> null
                }
            }
        }
    }

}
