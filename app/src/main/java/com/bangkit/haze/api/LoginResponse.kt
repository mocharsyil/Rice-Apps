package com.bangkit.haze.api

import com.google.gson.annotations.SerializedName
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

data class LoginResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginData?
) {
    companion object {
        fun fromJson(json: String): LoginResponse? {
            // Parse the JSON response and create a LoginResponse object
            // Refer to your JSON parsing library or manually parse the JSON here
            return null // Replace with the parsed LoginResponse object
        }
    }
}

data class LoginData(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("id") val id: String
)

