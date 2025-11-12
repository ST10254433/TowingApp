package com.school.towappmvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.school.towappmvp.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example contact info
        binding.tvCompanyName.text = "TowApp MVP"
        binding.tvEmail.text = "support@towappmvp.com"
        binding.tvPhone.text = "+27 65 711 1181"

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
