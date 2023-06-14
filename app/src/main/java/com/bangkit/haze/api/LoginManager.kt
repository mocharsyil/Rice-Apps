package com.bangkit.haze.api

import android.content.ContentValues.TAG
import android.util.Log
import com.bangkit.haze.LoginActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

// ...

class LoginManager {

    private val client = OkHttpClient()

    fun login(username: String, password: String, activity: LoginActivity) {
        val url = "http://34.128.123.145:5000/authentications"

        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle network error
                activity.onLoginFailure("Network error")
                Log.d(TAG, "Network request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val jsonObject = JSONObject(responseBody)

                if (response.isSuccessful) {
                    val status = jsonObject.getString("status")
                    if (status == "success") {
                        val accessToken = jsonObject.getJSONObject("data").getString("accessToken")
                        activity.onLoginSuccess(accessToken)
                    } else {
                        activity.onLoginFailure("Authentication failed")
                    }
                } else {
                    val message = jsonObject.getString("message")
                    activity.onLoginFailure(message)
                }
            }
        })
    }

    // ...
}
