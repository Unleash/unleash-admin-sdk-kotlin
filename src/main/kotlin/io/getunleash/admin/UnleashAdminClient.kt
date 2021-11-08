package io.getunleash.admin

import com.google.gson.Gson
import io.getunleash.admin.data.UnleashProject
import io.getunleash.admin.data.UnleashProjectResponse
import io.getunleash.admin.exceptions.UnleashIllegalContentTypeException
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.Duration
import kotlin.jvm.Throws

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

    private val _internalClient: OkHttpClient = client ?: OkHttpClient.Builder().addInterceptor { chain ->
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

    val gson: Gson = Gson()


    @Throws(UnleashIllegalContentTypeException::class)
    fun getProjects(): List<UnleashProject> {
        val request = Request.Builder().url(UnleashUrls.projectsUrl(serverUrl)).get().build()
        _internalClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                val unleashProjectResponse = gson.fromJson(response.body?.string(), UnleashProjectResponse::class.java)
                unleashProjectResponse.projects
            } else {
                when(response.code) {
                    415 -> {
                        val contentTypeHeader = response.request.header("Content-Type") ?: "No Content-Type header was set"
                        throw UnleashIllegalContentTypeException(message = "Illegal Content type caused a 415", contentTypeHeader = contentTypeHeader)
                    }
                    else -> emptyList()
                }
            }
        }
    }

}
