package com.bangkit.haze

import RiceDiseaseAdapter
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.adapter.BinaryGuideAdapter
//import com.bangkit.haze.adapter.MulticlassGuideAdapter
import com.bangkit.haze.api.ImagePredictionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DashboardActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_CAMERA = 100
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var recyclerViewHome: RecyclerView
    private lateinit var adapterHome: RiceDiseaseAdapter
    private lateinit var recyclerViewBinary: RecyclerView
    private lateinit var adapterBinary: BinaryGuideAdapter
    private lateinit var recyclerViewMulticlass: RecyclerView
//    private lateinit var adapterMulticlass: MulticlassGuideAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private var selectedImageFile: File? = null
    private lateinit var predictionManager: ImagePredictionManager

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



//        val binaryGuide = getBinaryGuide()
//        adapterBinary = BinaryGuideAdapter(binaryGuide)
//        recyclerViewBinary.adapter = adapterBinary


//        val multiclassGuide = getMulticlassGuide()
//        adapterMulticlass = MulticlassGuideAdapter(multiclassGuide)
//        recyclerViewMulticlass.adapter = adapterMulticlass

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    WelcomeTextView.visibility = View.VISIBLE
                    recyclerViewHome.visibility = View.VISIBLE
                    recyclerViewBinary.visibility = View.GONE
                    recyclerViewMulticlass.visibility = View.GONE
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.menu_binary -> {
                    openCamera()

//                    WelcomeTextView.visibility = View.GONE
//                    recyclerViewHome.visibility = View.GONE
//                    recyclerViewBinary.visibility = View.VISIBLE
//                    recyclerViewMulticlass.visibility = View.GONE
//                    fragmentContainer.visibility = View.GONE
                    true
                }
//                R.id.menu_multiclass -> {
//                    WelcomeTextView.visibility = View.GONE
//                    recyclerViewHome.visibility = View.GONE
//                    recyclerViewBinary.visibility = View.GONE
//                    recyclerViewMulticlass.visibility = View.VISIBLE
//                    fragmentContainer.visibility = View.GONE
//                    true
//                }
                R.id.menu_profile -> {
                    if (fragmentContainer.visibility == View.GONE) {
                        showProfilePage()
                    }
                    true
                }
                else -> false
            }
        }
        predictionManager = ImagePredictionManager()

        // Request camera permission if not granted
        if (!isCameraPermissionGranted()) {
            requestCameraPermission()
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_PERMISSION_CAMERA
        )
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                // Camera permission denied
                // Handle the case when the user denies the camera permission
                // You can show a message or take an alternative action
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                // Convert the bitmap to a file
                selectedImageFile = createImageFileFromBitmap(it)

                // Perform image prediction
                selectedImageFile?.let { file ->
                    predictionManager.predictImage(file, object : ImagePredictionManager.PredictionCallback {
                        override fun onPredictionSuccess(result: String) {
                            // Handle the successful prediction result
                            // The 'result' parameter contains the prediction response
                            // Show the result or perform any desired action
                            showPredictionResult(result)
                        }

                        override fun onPredictionFailure(errorMessage: String) {
                            // Handle the prediction failure
                            // The 'errorMessage' parameter contains the error message
                            // Show an error message or perform any desired action
                            Log.e("PredictionError", "Prediction failed: $errorMessage")
                            showPredictionError(errorMessage)
                        }
                    })
                }
            }
        }
    }

    private fun showPredictionResult(result: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Prediction Result")
        dialogBuilder.setMessage(result)
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        dialogBuilder.show()
    }

    private fun showPredictionError(errorMessage: String) {
        Log.d("PredictionError", errorMessage)
        runOnUiThread {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Prediction Error")
            dialogBuilder.setMessage(errorMessage)
            dialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            dialogBuilder.show()
        }
    }



    private fun createImageFileFromBitmap(bitmap: Bitmap): File? {
        val file = File(externalCacheDir, "temp_image.jpg")
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    private fun showProfilePage() {
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
        val WelcomeTextView = findViewById<TextView>(R.id.WelcomeTextView)
        fragmentContainer.visibility = View.VISIBLE
        recyclerViewHome.visibility = View.GONE
        recyclerViewBinary.visibility = View.GONE
        recyclerViewMulticlass.visibility = View.GONE
        WelcomeTextView.visibility = View.GONE
        val fragmentProfile = ProfileFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentProfile)
            .commit()
    }

    private fun getRiceDiseases(): List<RiceDisease> {
        val diseases = mutableListOf<RiceDisease>()
        diseases.add(RiceDisease("Brown Spot", "caused by the bacterium Xanthomonas oryzae pv. oryzicola. The symptoms of bacterial brown spot typically appear on the leaves of rice plants. They start as small, water-soaked lesions that later turn dark brown or black. These lesions often have a yellow halo surrounding them, which can help differentiate them from other types of leaf spots. As the disease progresses, the spots may expand and merge, leading to larger areas of necrosis on the leaves. In severe cases, the affected leaves can dry out, wither, and eventually die.",R.string.brownspot, R.drawable.brownspot))
        diseases.add(RiceDisease("Leaf Blast","Leaf blast is a significant disease in rice cultivation and can cause substantial yield losses if not managed effectively. It is characterized by the presence of small to large brown or grayish spots on the leaves, which may have a characteristic \"blast\" or diamond-shaped pattern. As the disease progresses, the spots may enlarge and coalesce, eventually causing the affected leaves to wither and die.", R.string.leafblast, R.drawable.leafblast))
        diseases.add(RiceDisease("Hispa","Hispa, also known as the rice hispa or striped rice hispa, is a common pest of rice plants. It is caused by the insect Hispa spp., particularly the species Dicladispa armigera and Dicladispa vigintioctopunctata.", R.string.hispa, R.drawable.leafblast))
        // Add more rice diseases as needed
        return diseases
    }

    private fun getBinaryGuide(): List<BinaryGuide> {
        val binary = mutableListOf<BinaryGuide>()
        binary.add(BinaryGuide("GUIDE", "Please Take a Photo like a photo above, to get the accurate output", R.drawable.login_image))
        // Add more binary guides as needed
        return binary
    }

    private fun getMulticlassGuide(): List<MulticlassGuide> {
        val multiclass = mutableListOf<MulticlassGuide>()
        multiclass.add(MulticlassGuide("GUIDE", "Please Take a Photo like a photo above, to get the accurate output", R.drawable.login_image))
        // Add more multiclass guides as needed
        return multiclass
    }





}
