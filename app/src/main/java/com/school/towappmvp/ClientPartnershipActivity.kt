package com.school.towappmvp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ClientPartnershipActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_partnership)

        val etBusinessName = findViewById<EditText>(R.id.etBusinessName)
        val etBusinessEmail = findViewById<EditText>(R.id.etBusinessEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitPartnership)

        btnSubmit.setOnClickListener {
            val name = etBusinessName.text.toString().trim()
            val email = etBusinessEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val partnershipData = mapOf(
                "name" to name,
                "email" to email,
                "phone" to phone,
                "address" to address,
                "timestamp" to System.currentTimeMillis()
            )

            val dbRef = FirebaseDatabase.getInstance().getReference("partnerships")
            val partnershipId = dbRef.push().key

            partnershipId?.let {
                dbRef.child(it).setValue(partnershipData)
                    .addOnSuccessListener { showSuccessDialog() }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Thank You 🙏")
            .setMessage("Your partnership request has been received. We’ll get in touch soon!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
