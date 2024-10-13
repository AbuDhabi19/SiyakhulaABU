package com.example.siyakhula

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AdminDashboard : AppCompatActivity() {

    private lateinit var manageUsersButton: Button
    private lateinit var programManagementButton: Button
    private lateinit var volunteerManagementButton: Button
    private lateinit var adminSignoutButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // Initializing buttons
        manageUsersButton = findViewById(R.id.manageUsersButton)
        programManagementButton = findViewById(R.id.ProgramMangementButton)
        volunteerManagementButton = findViewById(R.id.VolunteerManagementButton)
        adminSignoutButton = findViewById(R.id.adminSignoutButton)

        // Setting up click listeners for admin-specific actions
        manageUsersButton.setOnClickListener {
            startActivity(Intent(this, ManageUsers::class.java))
        }

        programManagementButton.setOnClickListener {
            startActivity(Intent(this, ProgramManagement::class.java))
        }

        volunteerManagementButton.setOnClickListener {
            // Navigate to Volunteer Management page (to be implemented)
            startActivity(Intent(this, VolunteerManagement::class.java))
        }

        adminSignoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
