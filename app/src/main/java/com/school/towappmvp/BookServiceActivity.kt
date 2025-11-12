package com.school.towappmvp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BookServiceActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_service)

        auth = FirebaseAuth.getInstance()

        val spnServiceType = findViewById<Spinner>(R.id.spnServiceType)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etDetails = findViewById<EditText>(R.id.etDetails)
        val btnBookService = findViewById<Button>(R.id.btnBookService)

        btnBookService.setOnClickListener {
            val serviceType = spnServiceType.selectedItem.toString()
            val location = etLocation.text.toString().trim()
            val details = etDetails.text.toString().trim()

            if (location.isEmpty()) {
                Toast.makeText(this, "Please enter your pickup location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = auth.currentUser
            if (user == null) {
                Toast.makeText(this, "Please log in to book a service", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bookingData = mapOf(
                "serviceType" to serviceType,
                "location" to location,
                "details" to details,
                "userEmail" to user.email,
                "timestamp" to System.currentTimeMillis()
            )

            val dbRef = FirebaseDatabase.getInstance().getReference("bookings")
            val bookingId = dbRef.push().key

            bookingId?.let {
                dbRef.child(it).setValue(bookingData).addOnSuccessListener {
                    showSuccessDialog()
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Booking Successful ✅")
            .setMessage("Your service request has been submitted. Our team will contact you shortly.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
