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
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.adapter.BinaryGuideAdapter
import com.bangkit.haze.adapter.MulticlassGuideAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.ByteArrayOutputStream
import java.io.File

class DashboardActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_CAMERA = 100
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var recyclerViewHome: RecyclerView
    private lateinit var adapterHome: RiceDiseaseAdapter
    private lateinit var recyclerViewBinary: RecyclerView
    private lateinit var adapterBinary: BinaryGuideAdapter
    private lateinit var recyclerViewMulticlass: RecyclerView
    private lateinit var adapterMulticlass: MulticlassGuideAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fragmentContainer = findViewById(R.id.fragmentContainer)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val WelcomeTextView: TextView = findViewById(R.id.WelcomeTextView)
        recyclerViewHome = findViewById(R.id.recyclerViewDiseases)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)

        recyclerViewBinary = findViewById(R.id.recyclerViewBinary)
        recyclerViewBinary.layoutManager = LinearLayoutManager(this)

        recyclerViewMulticlass = findViewById(R.id.recyclerViewMulticlass)
        recyclerViewMulticlass.layoutManager = LinearLayoutManager(this)

        val riceDiseases = getRiceDiseases()
        adapterHome = RiceDiseaseAdapter(riceDiseases)
        recyclerViewHome.adapter = adapterHome

        val binaryGuide = getBinaryGuide()
        adapterBinary = BinaryGuideAdapter(binaryGuide)
        recyclerViewBinary.adapter = adapterBinary

        val multiclassGuide = getMulticlassGuide()
        adapterMulticlass = MulticlassGuideAdapter(multiclassGuide)
        recyclerViewMulticlass.adapter = adapterMulticlass

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Handle item 1 click
                    WelcomeTextView.visibility = View.VISIBLE
                    recyclerViewHome.visibility = View.VISIBLE
                    recyclerViewBinary.visibility = View.GONE
                    recyclerViewMulticlass.visibility = View.GONE
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.menu_binary -> {
//                    checkCameraPermissionAndOpenCamera()
                    WelcomeTextView.visibility = View.GONE
                    recyclerViewHome.visibility = View.GONE
                    recyclerViewBinary.visibility = View.VISIBLE
                    recyclerViewMulticlass.visibility = View.GONE
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.menu_multiclass -> {
//                    checkCameraPermissionAndOpenCamera()
                    WelcomeTextView.visibility = View.GONE
                    recyclerViewHome.visibility = View.GONE
                    recyclerViewBinary.visibility = View.GONE
                    recyclerViewMulticlass.visibility = View.VISIBLE
                    fragmentContainer.visibility = View.GONE

                    true
                }
                R.id.menu_profile -> {
                    // Handle item 4 click
                    if (fragmentContainer.visibility == View.GONE) {
                        showProfilePage()
                    }

                    true
                }
                else -> false
            }
        }
    }

    private fun showProfilePage() {
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
        val WelcomeTextView = findViewById<TextView>(R.id.WelcomeTextView)

        // Show the fragment container
        fragmentContainer.visibility = View.VISIBLE

        // Hide other views
        recyclerViewHome.visibility = View.GONE
        recyclerViewBinary.visibility = View.GONE
        recyclerViewMulticlass.visibility = View.GONE
        WelcomeTextView.visibility = View.GONE

        // Add code to display the profile page
        val fragmentProfile = ProfileFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentProfile)
            .commit()
    }



    private fun getRiceDiseases(): List<RiceDisease> {
        val diseases = mutableListOf<RiceDisease>()
        diseases.add(RiceDisease("Brown Spot", "Biasanya terjadi karena blablabla", R.drawable.brownspot))
        diseases.add(RiceDisease("Leaf Blast", "Biasanya terjadi karena blablabla", R.drawable.leafblast))
        // Add more rice diseases as needed

        return diseases
    }

    private fun getBinaryGuide(): List<BinaryGuide> {
        val binary = mutableListOf<BinaryGuide>()
        binary.add(BinaryGuide("GUIDE", "Please Take a Photo like a photo above, to get the accurate output", R.drawable.login_image))

        // Add more rice diseases as needed

        return binary
    }

    private fun getMulticlassGuide(): List<MulticlassGuide> {
        val multiclass = mutableListOf<MulticlassGuide>()
        multiclass.add(MulticlassGuide("GUIDE", "Please Take a Photo like a photo above, to get the accurate output", R.drawable.login_image))

        // Add more rice diseases as needed

        return multiclass
    }

    fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
        }
    }

    fun openCamera() {
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
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
}

