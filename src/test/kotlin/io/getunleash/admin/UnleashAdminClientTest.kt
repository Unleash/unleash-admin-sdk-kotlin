package io.getunleash.admin

import io.getunleash.admin.test.TestResponses
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class UnleashAdminClientTest {

    @Test
    fun `can fire a request for list of projects`() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200).setBody(TestResponses.successfulOssProjectResponse))
        val client = UnleashAdminClient(server.url(""), "test")
        val projects = client.getProjects()
        assertThat(projects).hasSize(1)
        assertThat(projects.first().name).isEqualTo("Default")
        assertThat(projects.first().id).isEqualTo("default")
        assertThat(projects.first().description).isEqualTo("Default project")
        val request = server.takeRequest()
        assertThat(request.requestUrl.toString()).endsWith("/api/admin/projects")
    }

    @Test
    fun `can get list of feature toggles`() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200).setBody(TestResponses.successfulOssFeaturesResponse))
        val client = UnleashAdminClient(server.url(""), "test")
        val features = client.getFeatures("default")
        assertThat(features).hasSize(5)
    }

    @Test
    fun `can create toggle`() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(201).setBody(TestResponses.successfulOssFeatureCreateResponse))
        val client = UnleashAdminClient(server.url(""), "test")
        val feature = client.createFeature("default", "my.test.toggle", "Some fancy description")
        assertThat(feature!!.createdAt!!.toLocalDate()).isEqualTo(ZonedDateTime.now().toLocalDate())
    }
}
