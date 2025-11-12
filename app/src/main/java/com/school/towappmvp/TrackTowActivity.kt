package com.school.towappmvp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TrackTowActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var progressTow: ProgressBar
    private lateinit var btnRefreshStatus: Button
    private lateinit var btnBookTow: Button
    private lateinit var btnViewPartners: Button
    private lateinit var btnCallSupport: Button
    private lateinit var btnOpenMaps: Button
    private lateinit var tvPartners: TextView
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_tow)

        tvStatus = findViewById(R.id.tvStatus)
        progressTow = findViewById(R.id.progressTow)
        btnRefreshStatus = findViewById(R.id.btnRefreshStatus)
        btnBookTow = findViewById(R.id.btnBookTow)
        btnViewPartners = findViewById(R.id.btnViewPartners)
        btnCallSupport = findViewById(R.id.btnCallSupport)
        btnOpenMaps = findViewById(R.id.btnOpenMaps)
        tvPartners = findViewById(R.id.tvPartners)

        dbRef = FirebaseDatabase.getInstance().getReference("partners")

        // Fetch partner garages from Firebase
        loadPartners()

        // Refresh status button
        btnRefreshStatus.setOnClickListener {
            progressTow.visibility = View.VISIBLE
            tvStatus.text = "Fetching current status..."
            btnRefreshStatus.postDelayed({
                progressTow.visibility = View.GONE
                tvStatus.text = "Tow truck is on the way 🚚"
                Toast.makeText(this, "Status updated!", Toast.LENGTH_SHORT).show()
            }, 2000)
        }

        // Book a Tow
        btnBookTow.setOnClickListener {
            Toast.makeText(this, "Tow booking confirmed ✅", Toast.LENGTH_LONG).show()
        }

        // View partners
        btnViewPartners.setOnClickListener {
            Toast.makeText(this, "Opening partner list...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PartnerListActivity::class.java))
        }

        // Call Support
        btnCallSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+27123456789"))
            startActivity(intent)
        }

        // Open Maps
        btnOpenMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:-26.2041,28.0473?q=towing+service")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun loadPartners() {
        dbRef.get().addOnSuccessListener {
            if (it.exists()) {
                val partners = it.children.joinToString(", ") { child ->
                    child.child("name").value.toString()
                }
                tvPartners.text = "Partners: $partners"
            } else {
                tvPartners.text = "No partners found."
            }
        }.addOnFailureListener {
            tvPartners.text = "Failed to load partners."
        }
    }
}
