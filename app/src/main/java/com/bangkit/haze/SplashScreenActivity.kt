package com.bangkit.haze

import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

//    private lateinit var onBoardingScreen: SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash_screen)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
//            val isFirstTime = onBoardingScreen.getBoolean("firstTime", true)
//            if (isFirstTime) {
//                val editor = onBoardingScreen.edit()
//                editor.putBoolean("firstTime", false)
//                editor.apply()
//                startActivity(Intent(this, OnBoardingActivity::class.java))
//                finish()
//            } else {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }, 3500)
//    }
}