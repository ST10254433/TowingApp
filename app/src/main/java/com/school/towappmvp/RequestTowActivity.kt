package com.school.towappmvp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RequestTowActivity : AppCompatActivity() {

    private lateinit var etCarDetails: EditText
    private lateinit var etLocation: EditText
    private lateinit var btnUploadPhoto: Button
    private lateinit var btnRequestTow: Button
    private lateinit var imgPreview: ImageView

    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_tow)

        // Bind UI
        etCarDetails = findViewById(R.id.etCarDetails)
        etLocation = findViewById(R.id.etLocation)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        btnRequestTow = findViewById(R.id.btnRequestTow)
        imgPreview = findViewById(R.id.imgPreview) // we'll add this in XML

        // Upload photo
        btnUploadPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Submit request
        btnRequestTow.setOnClickListener {
            val details = etCarDetails.text.toString().trim()
            val location = etLocation.text.toString().trim()

            if (details.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedImageUri == null) {
                Toast.makeText(this, "Please upload a photo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Simulate "real" tow request
            Toast.makeText(
                this,
                "Tow Request Sent!\nCar: $details\nLocation: $location",
                Toast.LENGTH_LONG
            ).show()

            finish() // return to dashboard
        }
    }

    // Handle image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imgPreview.setImageURI(selectedImageUri)
            imgPreview.visibility = ImageView.VISIBLE
        }
    }
}
