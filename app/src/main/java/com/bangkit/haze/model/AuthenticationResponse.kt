package com.bangkit.haze.model

data class AuthenticationResponse(
    val status: String,
    val message: String,
    val data: AuthenticationData?
)

data class AuthenticationData(
    val accessToken: String,
    val refreshToken: String,
    val id: String
)