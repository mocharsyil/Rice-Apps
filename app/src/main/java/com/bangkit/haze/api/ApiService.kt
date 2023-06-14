package com.bangkit.haze.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("authentications")
    fun login(@Body request: LoginRequest): Call<LoginResponse>




@POST("users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}
