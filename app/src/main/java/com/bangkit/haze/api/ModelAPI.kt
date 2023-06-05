package com.bangkit.haze.api

import com.bangkit.haze.model.BinaryPredictResponse
import com.bangkit.haze.model.MulticlassPredictResponse

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

import retrofit2.http.*

interface ModelAPI {
    @Multipart
    @POST("binary_model_endpoint")
    fun predictBinaryImage(@Part("image") image: MultipartBody.Part): Call<BinaryPredictResponse>

    @Multipart
    @POST("multiclass_model_endpoint")
    fun predictMulticlassImage(@Part("image") image: MultipartBody.Part): Call<MulticlassPredictResponse>
}
