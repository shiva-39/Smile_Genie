package com.example.rtrp_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.example.rtrp_project.databinding.ActivityCaptureBinding

class Capture : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val REQUEST_IMAGE_CAPTURE = 123
    }


    private lateinit var binding: ActivityCaptureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraBtn.setOnClickListener {
            checkSelfPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "Oops! Something went wrong. No camera app found.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkSelfPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as? Bitmap
            if (bitmap != null) {
                detectFace(bitmap)
            }

        }
    }

    private fun detectFace(bitmap: Bitmap) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap, 0)

        detector.process(image)
            .addOnSuccessListener { faces ->
                val smilePercentages = faces.map { (it.smilingProbability ?: 0f) * 100 }
                if (smilePercentages.isEmpty()) {
                    Toast.makeText(this, "No face detected", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, Choice::class.java)
                    intent.putExtra("smile_percentages", smilePercentages.toFloatArray())
                    startActivity(intent)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}