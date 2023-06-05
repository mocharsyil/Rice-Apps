package com.bangkit.haze

import RiceDiseaseAdapter
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bangkit.haze.api.ModelAPI
import com.bangkit.haze.model.BinaryPredictResponse
import com.bangkit.haze.model.MulticlassPredictResponse
import com.google.android.material.bottomnavigation.BottomNavigationView


import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File

class DashboardActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_CAMERA = 100
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiceDiseaseAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        recyclerView = findViewById(R.id.recyclerViewDiseases)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val riceDiseases = getRiceDiseases()
        adapter = RiceDiseaseAdapter(riceDiseases)
        recyclerView.adapter = adapter

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Handle item 1 click
                    true
                }
                R.id.menu_binary -> {
                    checkCameraPermissionAndOpenCamera()
                    // Call binary model API
//                    val imageFile = File("path_to_image_file")
//                    val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
//                    val outputStream = ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                    val imageData = outputStream.toByteArray()
//                    // sendImageForPrediction(imageData, isBinary = true)
                    true
                }
                R.id.menu_multiclass -> {
                    checkCameraPermissionAndOpenCamera()
                    // Call multiclass model API
//                    val imageFile = File("path_to_image_file")
//                    val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
//                    val outputStream = ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                    val imageData = outputStream.toByteArray()
//                    // sendImageForPrediction(imageData, isBinary = false)
                    true
                }
                R.id.menu_profile -> {
                    // Handle item 4 click
                    true
                }
                else -> false
            }
        }
    }

    private fun getRiceDiseases(): List<RiceDisease> {
        val diseases = mutableListOf<RiceDisease>()
        diseases.add(RiceDisease("Brown Leaf", "Penyakit Brownleaf", R.drawable.login_image))
        diseases.add(RiceDisease("Hispa", "Penyakit Hispa", R.drawable.login_image))
        // Add more rice diseases as needed

        return diseases
    }

//    private fun sendImageForPrediction(imageData: ByteArray, isBinary: Boolean) {
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageData)
//        val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.example.com/") // Replace with the base URL of your API
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val modelAPI = retrofit.create(ModelAPI::class.java)
//        val call = if (isBinary) {
//            modelAPI.predictBinaryImage(imagePart)
//        } else {
//            modelAPI.predictMulticlassImage(imagePart)
//        }
//
//        call.enqueue(object : Callback<Any> {
//            override fun onResponse(
//                call: Call<Any>,
//                response: Response<Any>
//            ) {
//                if (response.isSuccessful) {
//                    if (isBinary) {
//                        val binaryResponse = response.body() as? BinaryPredictResponse
//                        if (binaryResponse != null) {
//                            // Process binary prediction response
//                            val label = binaryResponse.label
//                            val confidence = binaryResponse.confidence
//                            // ...
//                        }
//                    } else {
//                        val multiclassResponse = response.body() as? MulticlassPredictResponse
//                        if (multiclassResponse != null) {
//                            // Process multiclass prediction response
//                            val predictions = multiclassResponse.predictions
//                            // ...
//                        }
//                    }
//                } else {
//                    // Handle unsuccessful response
//                }
//            }
//
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                // Handle network or API call failure
//            }
//        })
//    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            // Process the captured image (imageBitmap) for prediction
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }
    }


}

