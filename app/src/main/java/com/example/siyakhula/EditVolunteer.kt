package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class EditVolunteer : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var volunteerKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_volunteer)

        firstNameEditText = findViewById(R.id.firstNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        saveButton = findViewById(R.id.saveButton)

        // Get the volunteer details from the intent
        volunteerKey = intent.getStringExtra("VOLUNTEER_KEY") ?: ""
        val firstName = intent.getStringExtra("FIRST_NAME") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        val phoneNumber = intent.getStringExtra("PHONE_NUMBER") ?: ""

        // Populate the EditText fields with existing details
        firstNameEditText.setText(firstName)
        emailEditText.setText(email)
        phoneNumberEditText.setText(phoneNumber)

        // Set onClickListener for the save button
        saveButton.setOnClickListener { updateVolunteerDetails() }
    }

    private fun updateVolunteerDetails() {
        val updatedFirstName = firstNameEditText.text.toString()
        val updatedEmail = emailEditText.text.toString()
        val updatedPhoneNumber = phoneNumberEditText.text.toString()

        if (updatedFirstName.isEmpty() || updatedEmail.isEmpty() || updatedPhoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the volunteer details in Firebase
        val database = FirebaseDatabase.getInstance().getReference("mediashare")
        val updatedVolunteer = mapOf(
            "FirstName" to updatedFirstName,
            "Email" to updatedEmail,
            "PhoneNumber" to updatedPhoneNumber
        )

        database.child(volunteerKey).updateChildren(updatedVolunteer).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Volunteer details updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity
            } else {
                Toast.makeText(this, "Failed to update details: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}