package com.bangkit.haze.api

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object APIManager {
    private const val BASE_URL = "http://34.128.123.145:5000"

    fun registerUser(
        username: String,
        password: String,
        fullname: String,
        callback: Callback
    ) {
        val url = "$BASE_URL/users"

        // Create a JSON object with the request payload
        val requestBody = JSONObject()
            .put("username", username)
            .put("password", password)
            .put("fullname", fullname)
            .toString()

        // Create the request body
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBodyObject = requestBody.toRequestBody(mediaType)

        // Create the request
        val request = Request.Builder()
            .url(url)
            .post(requestBodyObject)
            .build()

        // Create the OkHttp client
        val client = OkHttpClient()

        // Make the API request asynchronously
        client.newCall(request).enqueue(callback)
    }
}
