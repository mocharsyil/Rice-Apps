package com.bangkit.haze

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity





class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

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

        buttonLogin.setOnClickListener {
            val name = editTextName.text.toString()
            val password = editTextPassword.text.toString()

            // Perform login validation here
            // Example code:
            if (name == "admin" && password == "password") {
                // Login successful, navigate to the next activity
                // Replace MainActivity::class.java with your desired activity
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Show error message or handle invalid login credentials
                Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
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
