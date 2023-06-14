//package com.bangkit.haze
//
//import android.content.Context
//import android.content.ContextWrapper
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
//import com.bangkit.haze.databinding.ActivityDetailBinding
//import com.bangkit.haze.helper.BitmapHelper
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//import java.io.OutputStream
//
//
//class DetailActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDetailBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val bitmap = BitmapHelper.instance.bitmap
//
//        if (bitmap != null) {
//            val uri = bitmapToFile(bitmap)
//            binding.imageCamera.setImageURI(uri)
//
////            binding.category.text = uri.toString()
//            var file = ContextWrapper(applicationContext).getDir("Images", Context.MODE_PRIVATE)
//            file = File(file, "images.jpg")
//            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//
//            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
//
////            ApiClient.apiInstances
////                .uploadFile(body,requestFile)
////                .enqueue()
//            binding.category.text = getString(R.string.dummy_category)
//            binding.detailCategory.text = getString(R.string.dummy_detail)
//        } else {
//            Toast.makeText(applicationContext,"bitmap not found.",Toast.LENGTH_SHORT).show()
//        }
//
//        binding.buttonBack.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.license.setOnClickListener {
//            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
//        }
//    }
//
//    private fun bitmapToFile(bitmap: Bitmap): Uri {
//
//        val wrapper = ContextWrapper(applicationContext)
//        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
//        file = File(file, "images.jpg")
//
//        try {
//            val stream: OutputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//            stream.flush()
//            stream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return Uri.parse(file.absolutePath)
//    }
//}