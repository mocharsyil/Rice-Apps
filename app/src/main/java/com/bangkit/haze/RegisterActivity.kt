package com.bangkit.haze

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.haze.api.RegisterManager
import okhttp3.Call
import okhttp3.Callback

import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button

    private val registerManager = RegisterManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backButton: ImageButton = findViewById(R.id.backButton2)
        backButton.setOnClickListener {
            onBackPressed() // Navigate to the previous activity
        }

        editTextName = findViewById(R.id.registerFullName)
        editTextUsername = findViewById(R.id.registerUsername)
        editTextPassword = findViewById(R.id.registerPassword)
        editTextConfirmPassword = findViewById(R.id.registerConfirmPassword)
        buttonRegister = findViewById(R.id.register)

        buttonRegister.setOnClickListener {
            val fullname = editTextName.text.toString()
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            // Validate input fields
            if (fullname.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Make the API call
            registerManager.register(username, password, fullname, object : RegisterManager.RegisterCallback {
                override fun onRegistrationSuccess() {
                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Registration", "Registration successful")

                        // Navigate to the login activity
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onRegistrationFailure(errorMessage: String) {
                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Failed to register user: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(ContentValues.TAG, "Registration unsuccessful: $errorMessage")
                        // Handle registration failure here
                        // For example, you can display an error message or perform any other desired action
                    }
                }
            })
        }

        val notHaveAnAccountTextView = findViewById<TextView>(R.id.not_have_an)
        notHaveAnAccountTextView.setOnClickListener {
            val loginText = getString(R.string.login)
            val notHaveAnAccountText = notHaveAnAccountTextView.text.toString()
            val registerIndex = notHaveAnAccountText.indexOf(loginText)
            if (registerIndex != -1) {
                // Handle register click event
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
