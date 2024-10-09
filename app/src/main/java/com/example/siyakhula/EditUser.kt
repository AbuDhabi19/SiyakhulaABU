package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditUser : AppCompatActivity() {
    private lateinit var userNameEditText: EditText
    private lateinit var userEmailEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var user: User
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        userNameEditText = findViewById(R.id.userName)
        userEmailEditText = findViewById(R.id.userEmail)
        updateButton = findViewById(R.id.btn_update_user)

        // Get the user data from the intent
        user = intent.getParcelableExtra("User") ?: return

        // Populate the fields with the current user details
        userNameEditText.setText(user.name)
        userEmailEditText.setText(user.email)

        updateButton.setOnClickListener {
            val updatedName = userNameEditText.text.toString()
            val updatedEmail = userEmailEditText.text.toString()

            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty()) {
                updateUserInFirestore(updatedName, updatedEmail)
                startActivity(Intent(this, ManageUsers::class.java)) // Navigate back to ManageUsers
            } else {
                Toast.makeText(this, "User name and email cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInFirestore(updatedName: String, updatedEmail: String) {
        val userRef = db.collection("Users").document(user.id)
        userRef.update("name", updatedName, "email", updatedEmail)
            .addOnSuccessListener {
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity and return to the previous one
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error updating user", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to return to the User Management screen
    fun returnClick(view: View) {
        startActivity(Intent(this, ManageUsers::class.java))
    }
}
