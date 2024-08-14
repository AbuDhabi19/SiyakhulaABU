package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var isAdmin: CheckBox
    private lateinit var isClient: CheckBox
    private lateinit var registerBtn: Button
    private lateinit var goToLogin: Button
    private var valid = true

    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth and Firestore
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        fullName = findViewById(R.id.registerName)
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        phone = findViewById(R.id.registerPhone)
        isAdmin = findViewById(R.id.isTeacher)  // Checkbox for admin
        isClient = findViewById(R.id.isStudent)  // Checkbox for client
        registerBtn = findViewById(R.id.registerBtn)
        goToLogin = findViewById(R.id.gotoLogin)

        registerBtn.setOnClickListener {
            if (checkField(fullName) && checkField(email) && checkField(password) && checkField(phone)) {
                // Start the user registration process
                fAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult ->
                        val user = fAuth.currentUser
                        Toast.makeText(this@Register, "Account Created", Toast.LENGTH_SHORT).show()

                        val df = fStore.collection("Users").document(user!!.uid)
                        val userInfo = hashMapOf(
                            "FullName" to fullName.text.toString(),
                            "UserEmail" to email.text.toString(),
                            "PhoneNumber" to phone.text.toString()
                        )

                        if (isAdmin.isChecked) {
                            userInfo["isAdmin"] = "1" // Indicate that this user is an admin
                        } else if (isClient.isChecked) {
                            userInfo["isUser"] = "1"  // Indicate that this user is a client
                        }

                        df.set(userInfo).addOnSuccessListener {
                            if (isAdmin.isChecked) {
                                startActivity(Intent(applicationContext, Admin::class.java))
                            } else {
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                            }
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@Register, "Failed to Create Account", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        goToLogin.setOnClickListener {
            // Navigate to login activity
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun checkField(textField: EditText): Boolean {
        return if (textField.text.toString().isEmpty()) {
            textField.error = "Error"
            false
        } else {
            true
        }
    }
}
