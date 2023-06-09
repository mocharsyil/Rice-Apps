package com.bangkit.haze


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import com.bangkit.haze.api.LoginManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
=======
import com.bangkit.haze.api.ApiClient
import com.bangkit.haze.api.AuthService
import com.bangkit.haze.model.AuthenticationRequest
import com.bangkit.haze.model.AuthenticationResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
>>>>>>> 5acf431ab61aeab763e4ee7a8a684e54505fa377

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var authService: AuthService

    private val loginManager = LoginManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // Navigate to the previous activity
        }

        editTextName = findViewById(R.id.loginName)
        editTextPassword = findViewById(R.id.loginPassword)
        buttonLogin = findViewById(R.id.login)

        authService = ApiClient.createService(AuthService::class.java)


        buttonLogin.setOnClickListener {
            val username = editTextName.text.toString()
            val password = editTextPassword.text.toString()

<<<<<<< HEAD
            // Perform login operation
            loginManager.login(username, password, this)
=======
            // Create authentication request
            val request = AuthenticationRequest(name, password)

            // Call the authentication API
            val call = authService.authenticate(request)
            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        // Login successful, get the access token
                        val accessToken = response.body()?.data?.accessToken

                        // Save the access token to a shared preference or wherever you store user session data
                        // ...

                        // Navigate to the next activity
                        // Replace DashboardActivity::class.java with your desired activity
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Show error message or handle invalid login credentials
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid credentials",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    // Show error message or handle API call failure
                    val errorMessage = "Failed to authenticate"
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()

                    // Log the failure message
                    Log.d("LoginActivity", errorMessage)
                }

            })
>>>>>>> 5acf431ab61aeab763e4ee7a8a684e54505fa377
        }

        val notHaveAnAccountTextView = findViewById<TextView>(R.id.not_have_an)
        notHaveAnAccountTextView.setOnClickListener {
            val registerText = getString(R.string.register)
            val notHaveAnAccountText = notHaveAnAccountTextView.text.toString()
            val registerIndex = notHaveAnAccountText.indexOf(registerText)
            if (registerIndex != -1) {
                // Handle register click event
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun onLoginSuccess(token: String) {
        runOnUiThread {
            // Handle successful login
            Toast.makeText(this, "Login successful. Token: $token", Toast.LENGTH_SHORT).show()
            // You can store the token or perform any other required actions

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun onLoginFailure(errorMessage: String? = null) {
        runOnUiThread {
            // Handle unsuccessful login
            Toast.makeText(this, "Login unsuccessful. $errorMessage", Toast.LENGTH_SHORT).show()
            // You can display an error message or perform any other required actions
        }
    }




}
