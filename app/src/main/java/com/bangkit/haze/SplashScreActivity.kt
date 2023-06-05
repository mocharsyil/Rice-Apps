package com.bangkit.haze

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager


class SplashScreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scre)
        // Hide the status bar (top bar) of the phone
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val moveLogin = findViewById<Button>(R.id.moveLogin)
        val moveRegister = findViewById<Button>(R.id.Moveregister)

        moveLogin.setOnClickListener {
            // Handle moveLogin button click
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        moveRegister.setOnClickListener {
            // Handle moveRegister button click
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
    }
}
}
