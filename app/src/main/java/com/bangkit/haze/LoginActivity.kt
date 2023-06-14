package com.bangkit.haze


import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.haze.api.LoginManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

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

        buttonLogin.setOnClickListener {
            val username = editTextName.text.toString()
            val password = editTextPassword.text.toString()

            // Perform login operation
            loginManager.login(username, password, this)
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
