package com.example.siyakhula

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddUser : AppCompatActivity() {
    private lateinit var userNameEditText: EditText
    private lateinit var userEmailEditText: EditText
    private lateinit var userPasswordEditText: EditText
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        // Initialize the EditText fields
        userNameEditText = findViewById(R.id.userName)
        userEmailEditText = findViewById(R.id.userEmail)
        userPasswordEditText = findViewById(R.id.userPassword)

        // Set an onClick listener for the "Add User" button
        val addUserButton: Button = findViewById(R.id.AddUserBTNClick)
        addUserButton.setOnClickListener {
            val userName = userNameEditText.text.toString()
            val userEmail = userEmailEditText.text.toString()
            val userPassword = userPasswordEditText.text.toString()

            // Validate input before saving
            if (userName.isNotEmpty() && userEmail.isNotEmpty()) {
                addUserToFirestore(userName, userEmail, userPassword)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to add user to Firestore
    private fun addUserToFirestore(name: String, email: String, password: String) {
        val newUser = User(name = name, email = email, password = password)

        // Add user data to Firestore
        db.collection("Users").add(newUser).addOnSuccessListener {
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
            // Redirect back to the user management page
            startActivity(Intent(this, ManageUsers::class.java))
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to add user: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to return to the User Management screen
    fun returnClick(view: View) {
        startActivity(Intent(this, ManageUsers::class.java))
    }

}
