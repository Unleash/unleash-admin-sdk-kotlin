package io.getunleash.admin

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UnleashUrlsTest {
    val baseUrl = "https://api.unleash-hosted.com/demo".toHttpUrl()

    @Test
    fun `resolves projects url correctly`() {
        val projectsUrl = UnleashUrls.projectsUrl(baseUrl)
        Assertions.assertThat(projectsUrl.pathSegments).hasSize(4)
        Assertions.assertThat(projectsUrl.pathSegments.last()).isEqualTo("projects")
        Assertions.assertThat(projectsUrl.toString()).isEqualTo("https://api.unleash-hosted.com/demo/api/admin/projects")
    }

    @Test
    fun `resolves url for a single project correctly`() {
        val projectUrl = UnleashUrls.projectUrl(baseUrl, "default")
        Assertions.assertThat(projectUrl.pathSegments).hasSize(5)
        Assertions.assertThat(projectUrl.pathSegments.last()).isEqualTo("default")
        Assertions.assertThat(projectUrl.toString()).isEqualTo("https://api.unleash-hosted.com/demo/api/admin/projects/default")
    }

    @Test
    fun `resolves url for features correctly`() {
        val featuresUrl = UnleashUrls.featuresUrl(baseUrl, "default")
        Assertions.assertThat(featuresUrl.pathSegments).hasSize(6)
        Assertions.assertThat(featuresUrl.pathSegments.last()).isEqualTo("features")
        Assertions.assertThat(featuresUrl.toString()).isEqualTo("https://api.unleash-hosted.com/demo/api/admin/projects/default/features")
    }
}
