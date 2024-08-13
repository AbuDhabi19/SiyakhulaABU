package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var gotoRegister: Button
    private var valid = true
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        email = findViewById(R.id.loginEmail)
        password = findViewById(R.id.loginPassword)
        loginBtn = findViewById(R.id.loginBtn)
        gotoRegister = findViewById(R.id.gotoRegister)

        loginBtn.setOnClickListener {
            checkField(email)
            checkField(password)

            if (valid) {
                fAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult: AuthResult ->
                        Toast.makeText(this, "Logged in successfully.", Toast.LENGTH_SHORT).show()
                        checkUserAccessLevel(authResult.user?.uid ?: "")
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Login failed: ${exception.message}", Toast.LENGTH_SHORT).show()

                    }
            }
        }

        gotoRegister.setOnClickListener {
            // Navigate to Register activity
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun checkUserAccessLevel(uid: String) {
        val df: DocumentReference = fStore.collection("Users").document(uid)
        // Extract the data from the document
        df.get().addOnSuccessListener { documentSnapshot ->
            Log.d("TAG", "onSuccess: ${documentSnapshot.data}")
            // Identify the user access level
            if (documentSnapshot.getString("isAdmin") != null) {
                // User is admin
                startActivity(Intent(applicationContext, Admin::class.java))
                finish()
            } else if (documentSnapshot.getString("isUser") != null) {
                // User is a normal user
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Failed to fetch user data: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkField(textField: EditText): Boolean {
        return if (textField.text.toString().isEmpty()) {
            textField.error = "This field is required"
            valid = false
            false
        } else {
            true
        }
    }

    override fun onStart() {
        super.onStart()
        if (fAuth.currentUser != null) {
            // If user is already logged in, navigate to MainActivity
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}
