package com.example.siyakhula

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class AboutUs : AppCompatActivity() {

    private lateinit var bannerImage: ImageView
    private lateinit var siyakhulaAbout1: TextView
    private lateinit var siyakhulaAbout2: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us2)

        // Initialize views
        bannerImage = findViewById(R.id.bannerImage)
        siyakhulaAbout1 = findViewById(R.id.siyakhulaAbout1)
        siyakhulaAbout2 = findViewById(R.id.siyakhulaAbout2)
        button = findViewById(R.id.button)

        // You can add a click listener to the button here
        button.setOnClickListener {
            // Add your button click logic here
            finish()
        }
    }
}