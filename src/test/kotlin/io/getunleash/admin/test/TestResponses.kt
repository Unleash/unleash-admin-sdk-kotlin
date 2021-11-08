package io.getunleash.admin.test

object TestResponses {
    fun successfulOssProjectResponse(): String = """
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
}
