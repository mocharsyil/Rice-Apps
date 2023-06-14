package com.bangkit.haze.api

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class RegisterManager {

    private val client = OkHttpClient()

    interface RegisterCallback {
        fun onRegistrationSuccess()
        fun onRegistrationFailure(errorMessage: String)
    }

    fun register(username: String, password: String, fullname: String, callback: RegisterCallback) {
        val url = "http://34.128.123.145:5000/users"

        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)
        json.put("fullname", fullname)

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onRegistrationFailure("Network error")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val jsonObject = JSONObject(responseBody)

                if (response.isSuccessful) {
                    val status = jsonObject.getString("status")
                    if (status == "success") {
                        callback.onRegistrationSuccess()
                    } else {
                        callback.onRegistrationFailure("Registration failed")
                    }
                } else {
                    val message = jsonObject.getString("message")
                    callback.onRegistrationFailure(message)
                }
            }
        })
    }
}
