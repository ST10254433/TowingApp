package com.school.towappmvp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Reference to the logo ImageView
        val imgLogo = findViewById<ImageView>(R.id.imgLogo)

        // Start fade-in animation
        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))

        // Delay for 3 seconds, then go to Login or Main
        Handler(Looper.getMainLooper()).postDelayed({
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                // already logged in → go to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // not logged in → go to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000) // 3 seconds
    }
}
