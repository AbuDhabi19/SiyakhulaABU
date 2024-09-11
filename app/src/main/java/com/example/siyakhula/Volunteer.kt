package com.example.siyakhula

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.net.Uri

class Volunteer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.volunteer)

        // Initialize buttons
        val button = findViewById<Button>(R.id.Button1)
        val button2 = findViewById<Button>(R.id.button2)

        // Set button click listeners
        button.setOnClickListener {
            // Close the app when Button1 is clicked
            finish()
        }

        button2.setOnClickListener {
            // Open a blank Google Forms page when Button2 is clicked
            val url = "https://docs.google.com/forms/d/e/1FAIpQLSfYE2TcP8Q035a9dfYsR4405ODVGITeX47gtFyJ4qAf_ZKQgw/viewform?usp=sf_link"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}