package com.example.siyakhula


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Contact_Us : AppCompatActivity() {

    private lateinit var contactDonateBtn: Button
    private lateinit var returnContactBtn: Button

    private lateinit var instagram_button: ImageButton
    private lateinit var facebook_button: ImageButton
    private lateinit var youtube_button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        contactDonateBtn = findViewById(R.id.contactDonateBtn)
        returnContactBtn = findViewById(R.id.returnContactBtn)

        instagram_button = findViewById(R.id.instagram_button)
        facebook_button = findViewById(R.id.facebook_button)
        youtube_button = findViewById(R.id.youtube_button)

        contactDonateBtn.setOnClickListener{
            startActivity(Intent(this , Donations::class.java))
        }

        //Saving this block of code to use for the image buttons
      /*  val url = "https://account.stewardship.org.uk/gift/start/20327069?donationType=OneOff"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
        */

        instagram_button.setOnClickListener {

            val url = "https://www.instagram.com/siyakhula.sa/?hl=en"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

        }

        facebook_button.setOnClickListener {

            val url = "https://www.facebook.com/SiyakhulaSA"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

        }

        youtube_button.setOnClickListener {

            val url = "https://www.youtube.com/@SiyakhulaSA"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

        }

        returnContactBtn.setOnClickListener{
            startActivity(Intent(this , Dashboard::class.java))
        }
    }


}