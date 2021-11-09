package io.getunleash.admin.test

import java.time.format.DateTimeFormatter.ISO_DATE_TIME

object TestResponses {
    val successfulOssProjectResponse: String = """
        {
          "version": 1,
          "projects": [
            {
              "id": "default",
              "name": "Default",
              "description": "Default project",
              "createdAt": "2021-10-20T06:58:16.501Z",
              "health": 100,
              "featureCount": 5,
              "memberCount": 0
            }
          ]
        }
    """.trimIndent()

    val successfulOssFeaturesResponse = """
        {
          "version": 1,
          "features": [
            {
              "type": "release",
              "name": "this-is-fun",
              "createdAt": "2021-10-20T09:20:20.414Z",
              "lastSeenAt": null,
              "stale": false,
              "environments": [
                {
                  "name": "default",
                  "enabled": true,
                  "type": "production",
                  "sortOrder": 1
                }
              ]
            },
            {
              "type": "release",
              "name": "this-is-not-fun",
              "createdAt": "2021-10-20T09:20:20.414Z",
              "lastSeenAt": null,
              "stale": false,
              "environments": [
                {
                  "name": "default",
                  "enabled": false,
                  "type": "production",
                  "sortOrder": 1
                }
              ]
            },
            {
              "type": "release",
              "name": "test-feature-name",
              "createdAt": "2021-11-08T13:39:25.052Z",
              "lastSeenAt": null,
              "stale": false,
              "environments": [
                {
                  "name": "default",
                  "enabled": false,
                  "type": "production",
                  "sortOrder": 1
                }
              ]
            },
            {
              "type": "release",
              "name": "test-feature-name-2",
              "createdAt": "2021-11-08T13:39:41.578Z",
              "lastSeenAt": null,
              "stale": false,
              "environments": [
                {
                  "name": "default",
                  "enabled": false,
                  "type": "production",
                  "sortOrder": 1
                }
              ]
            },
            {
              "type": "release",
              "name": "Minbari",
              "createdAt": "2021-11-08T13:54:08.919Z",
              "lastSeenAt": null,
              "stale": false,
              "environments": [
                {
                  "name": "default",
                  "enabled": false,
                  "type": "production",
                  "sortOrder": 1
                }
              ]
            }
          ]
        }
    """.trimIndent()

    val successfulOssFeatureCreateResponse = """
        {
          "name": "test-feature-name-4",
          "description": null,
          "type": "release",
          "project": "default",
          "stale": false,
          "variants": null,
          "createdAt": "${java.time.ZonedDateTime.now().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME)}",
          "lastSeenAt": null
        }
    """.trimIndent()
}
