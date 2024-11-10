package com.example.siyakhula

// Import necessary Android and Firebase libraries
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Register activity class to handle user registration
class Register : AppCompatActivity() {

    // Declare UI elements
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button
    private lateinit var gotoLogin: Button

    // Firebase authentication and Firestore database references
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    // Boolean variable to check if input fields are valid
    private var valid = true

    // Override the onCreate method which is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Set the layout for this activity

        // Initialize the UI elements by binding them to corresponding views in the layout
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        registerBtn = findViewById(R.id.registerBtn)
        gotoLogin = findViewById(R.id.gotoLogin)

        // Initialize Firebase authentication and Firestore instances
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // Set click listener for the Register button
        registerBtn.setOnClickListener {
            // Check if the email and password fields are filled
            checkField(email)
            checkField(password)

            // If both fields are valid, proceed with registration
            if (valid) {
                registerUser(email.text.toString(), password.text.toString())
            }
        }

        // Set click listener for the "Go to Login" button, which navigates to the login screen
        gotoLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    // Function to check if an input field is empty
    private fun checkField(textField: EditText): Boolean {
        // If the field is empty, show an error message and set 'valid' to false
        return if (textField.text.toString().isEmpty()) {
            textField.error = "This field is required"
            valid = false
            false
        } else {
            // If the field is not empty, it's valid
            true
        }
    }

    // Function to register a user based on email domain
    private fun registerUser(email: String, password: String) {
        // If the email ends with "@siyakhula.org", register the user as an admin
        if (email.endsWith("@siyakhula.org")) {
            createUser(email, password, isAdmin = true)
        } else {
            // Otherwise, register the user as a normal user
            createUser(email, password, isAdmin = false)
        }
    }

    // Function to create a new user in Firebase Authentication and store user data in Firestore
    private fun createUser(email: String, password: String, isAdmin: Boolean) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = fAuth.currentUser

                // Send email verification link
                user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                    if (verifyTask.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Verification email sent to $email",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Store user data in Firestore
                        val userData = hashMapOf(
                            "email" to email,
                            if (isAdmin) "isAdmin" to true else "isUser" to true
                        )

                        fStore.collection("Users").document(user.uid).set(userData)
                            .addOnSuccessListener {
                                Log.d("TAG", "User data saved")
                                // Sign out the user to prevent login without verification
                                FirebaseAuth.getInstance().signOut()
                                startActivity(Intent(this, Login::class.java))
                                finish()
                            }.addOnFailureListener { e ->
                            Log.e("TAG", "Error saving user data", e)
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Registration failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
