package io.getunleash.admin

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import java.time.Duration

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

    val defaultClient: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val authedRequest = chain.request().newBuilder()
                .addHeader("Authorization", apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Unleash-Admin-Client", "unleash-admin-client")
                .addHeader(
                    "X-Unleash-Admin-Client-Version",
                    this@UnleashAdminClient.javaClass.`package`.implementationVersion
                )
                .build()
            chain.proceed(authedRequest)
        }
        .readTimeout(Duration.ofSeconds(5))
        .connectTimeout(Duration.ofSeconds(2))
    .build()

    val gson: Gson = Gson()

}
