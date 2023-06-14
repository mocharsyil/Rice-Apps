package com.bangkit.haze

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.haze.api.ApiClient
import com.bangkit.haze.api.AuthService
import com.bangkit.haze.model.AuthenticationRequest
import com.bangkit.haze.model.AuthenticationResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var authService: AuthService

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
            val name = editTextName.text.toString()
            val password = editTextPassword.text.toString()

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
}
