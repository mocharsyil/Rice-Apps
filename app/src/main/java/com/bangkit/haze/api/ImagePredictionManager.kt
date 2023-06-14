package com.bangkit.haze.api

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class ImagePredictionManager {

    private val client = OkHttpClient()

    interface PredictionCallback {
        fun onPredictionSuccess(result: String)
        fun onPredictionFailure(errorMessage: String)
    }

    private inner class PredictionCallbackImpl(private val callback: PredictionCallback) : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback.onPredictionFailure("Network error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                callback.onPredictionSuccess(responseBody)
            } else {
                callback.onPredictionFailure("Prediction failed")
            }
        }
    }

    fun predictImage(imageFile: File, callback: PredictionCallback) {
        val url = "https://api-ml-lbevkqepga-et.a.run.app/predict"

        val mediaType = "image/*".toMediaType()
        val requestBody = imageFile.asRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val errorMessage = "Network error: ${e.message}"
                Log.e("PredictionError", errorMessage)
                callback.onPredictionFailure(errorMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    callback.onPredictionSuccess(responseBody)
                } else {
                    val errorMessage = "Prediction failed: ${response.message}"
                    Log.e("PredictionError", errorMessage)
                    callback.onPredictionFailure(errorMessage)
                }
            }
        })
    }

    fun openCameraForPrediction(imageFile: File, callback: PredictionCallback) {
        predictImage(imageFile, callback)
    }


}
