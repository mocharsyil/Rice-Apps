//package com.bangkit.haze.api
//
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.DELETE
//import retrofit2.http.POST
//import retrofit2.http.PUT
//
//interface AuthApiService {
//    @POST("/authentications")
//    fun postAuthentication(@Body payload: AuthenticationPayload): Call<AuthResponse>
//
//    @PUT("/authentications")
//    fun putAuthentication(@Body payload: AuthenticationPayload): Call<AuthResponse>
//
//    @DELETE("/authentications")
//    fun deleteAuthentication(@Body payload: DeleteAuthenticationPayload): Call<DeleteAuthResponse>
//}
//
//data class AuthenticationPayload(val username: String, val password: String)
//
//data class AuthResponse(
//    val status: String,
//    val message: String,
//    val data: AuthData
//)
//
//data class AuthData(
//    val accessToken: String,
//    val refreshToken: String,
//    val id: String
//)
//
//data class DeleteAuthenticationPayload(val refreshToken: String)
//
//data class DeleteAuthResponse(
//    val status: String,
//    val message: String
//)
