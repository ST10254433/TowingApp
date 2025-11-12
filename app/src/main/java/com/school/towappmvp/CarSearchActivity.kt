package com.school.towappmvp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CarSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_search)

        val etNumberPlate = findViewById<EditText>(R.id.etNumberPlate)
        val btnSearchCar = findViewById<Button>(R.id.btnSearchCar)
        val tvCarResults = findViewById<TextView>(R.id.tvCarResults)

        btnSearchCar.setOnClickListener {
            val plate = etNumberPlate.text.toString().trim()
            if (plate.isNotEmpty()) {
                tvCarResults.text = "Results for $plate:\nModel: Toyota Corolla\nColor: Blue\nStatus: Clear"
            } else {
                tvCarResults.text = "Please enter a number plate"
            }
        }
    }
}
