package com.bangkit.haze

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val backButton: ImageButton = findViewById(R.id.backButton2)
        backButton.setOnClickListener {
            onBackPressed() // Navigate to the previous activity
        }

        val notHaveAnAccountTextView = findViewById<TextView>(R.id.not_have_an)
        notHaveAnAccountTextView.setOnClickListener {
            val registerText = getString(R.string.login)
            val notHaveAnAccountText = notHaveAnAccountTextView.text.toString()
            val registerIndex = notHaveAnAccountText.indexOf(registerText)
            if (registerIndex != -1) {
                // Handle register click event
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }


    }
}