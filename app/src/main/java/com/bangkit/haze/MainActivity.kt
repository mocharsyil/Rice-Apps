//package com.bangkit.haze
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.Bitmap
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.provider.MediaStore
//import android.widget.Toast
//import com.bangkit.haze.databinding.ActivityMainBinding
//import com.bangkit.haze.helper.BitmapHelper
//
//private const val REQUEST_CODE = 77
//private const val PREFS_NAME = "MyPrefs"
//private const val KEY_LOGGED_IN = "isLoggedIn"
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
//
//        if (isLoggedIn()) {
//            navigateMain()
//        } else {
//            BitmapHelper.instance.bitmap = null
//
//            binding.camera.setOnClickListener {
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//                if (takePictureIntent.resolveActivity(this.packageManager) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_CODE)
//                } else {
//                    Toast.makeText(this, "Unable to Open Camera", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    private fun isLoggedIn(): Boolean {
//        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
//    }
//
//    private fun navigateMain() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
//            val image = data?.extras?.get("data") as Bitmap
//            BitmapHelper.instance.bitmap = image
//
//            val intent = Intent(this, DetailActivity::class.java)
//            startActivity(intent)
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
//
//    private fun navigateLogin() {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//        // Update login status
//        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean(KEY_LOGGED_IN, true)
//        editor.apply()
//    }
//
//
//}
