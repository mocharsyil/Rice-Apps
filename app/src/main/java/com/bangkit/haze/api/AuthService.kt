package com.bangkit.haze.api

import com.bangkit.haze.model.AuthenticationRequest
import com.bangkit.haze.model.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/authentications")
    fun authenticate(@Body request: AuthenticationRequest): Call<AuthenticationResponse>
}
