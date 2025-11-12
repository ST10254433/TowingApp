package com.school.towappmvp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var prefs: SharedPreferences

    private var towedCount = 0
    private var trackedCount = 0

    private lateinit var tvWelcome: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var tvTotalTowedView: TextView
    private lateinit var tvTotalTrackedView: TextView
    private lateinit var btnLogout: TextView

    companion object {
        private const val PREFS_NAME = "tow_prefs"
        private const val KEY_TOWED = "towedCount"
        private const val KEY_TRACKED = "trackedCount"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val user: FirebaseUser? = auth.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        towedCount = prefs.getInt(KEY_TOWED, 0)
        trackedCount = prefs.getInt(KEY_TRACKED, 0)

        tvWelcome = findViewById(R.id.tvWelcome)
        imgProfile = findViewById(R.id.imgProfile)
        tvTotalTowedView = findViewById(R.id.tvTotalTowed)
        tvTotalTrackedView = findViewById(R.id.tvTotalTracked)
        btnLogout = findViewById(R.id.btnLogout)

        val cardRequests = findViewById<LinearLayout>(R.id.cardRequests)
        val cardVehicles = findViewById<LinearLayout>(R.id.cardVehicles)
        val cardSOS = findViewById<LinearLayout>(R.id.cardSOS)
        val cardSearchCars = findViewById<LinearLayout>(R.id.cardSearchCars)
        val cardBooking = findViewById<LinearLayout>(R.id.cardBooking)
        val cardPartners = findViewById<LinearLayout>(R.id.cardPartners)
        val cardContact = findViewById<LinearLayout>(R.id.cardContact)

        populateUserInfo(user)
        updateCountersUI()

        // --- Functional Buttons ---
        cardRequests.setOnClickListener {
            startActivity(Intent(this, RequestTowActivity::class.java))
            towedCount++
            saveCounters()
            updateCountersUI()
        }

        cardVehicles.setOnClickListener {
            startActivity(Intent(this, TrackTowActivity::class.java))
            trackedCount++
            saveCounters()
            updateCountersUI()
        }

        cardSOS.setOnClickListener {
            startActivity(Intent(this, SosActivity::class.java))
        }

        cardSearchCars.setOnClickListener {
            startActivity(Intent(this, CarSearchActivity::class.java))
        }

        cardBooking.setOnClickListener {
            startActivity(Intent(this, BookServiceActivity::class.java))
        }

        cardPartners.setOnClickListener {
            startActivity(Intent(this, ClientPartnershipActivity::class.java))
        }

        cardContact.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            try {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val googleClient = GoogleSignIn.getClient(this, gso)
                googleClient.signOut().addOnCompleteListener {
                    startLoginAfterSignOut()
                }
            } catch (e: Exception) {
                startLoginAfterSignOut()
            }
        }
    }

    private fun populateUserInfo(user: FirebaseUser) {
        val displayName = user.displayName ?: user.email ?: "User"
        tvWelcome.text = "Welcome, $displayName 👋"

        val photoUri: Uri? = user.photoUrl
        if (photoUri != null) {
            Glide.with(this)
                .load(photoUri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(imgProfile)
        } else {
            imgProfile.setImageResource(R.mipmap.ic_launcher_round)
        }
    }

    private fun updateCountersUI() {
        tvTotalTowedView.text = towedCount.toString()
        tvTotalTrackedView.text = trackedCount.toString()
    }

    private fun saveCounters() {
        prefs.edit().putInt(KEY_TOWED, towedCount)
            .putInt(KEY_TRACKED, trackedCount)
            .apply()
    }

    private fun startLoginAfterSignOut() {
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
