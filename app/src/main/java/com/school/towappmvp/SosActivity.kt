package com.school.towappmvp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)

        val btnSendSOS = findViewById<Button>(R.id.btnSendSOS)

        btnSendSOS.setOnClickListener {
            Toast.makeText(this, "🚨 SOS alert sent to nearby towing service!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
