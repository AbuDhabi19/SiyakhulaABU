package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VolunteerManagement : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var volunteerAdapter: VolunteerAdapter
    private val volunteerList: MutableList<VolunteerDataClass> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_volunteer_management)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchVolunteers() // Fetch data from Firebase
    }

    fun returnClick(view: View) {
        // Redirect to Admin Dashboard
        startActivity(Intent(this, AdminDashboard::class.java))
    }

    private fun fetchVolunteers() {
        val database = FirebaseDatabase.getInstance().getReference("mediashare")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                volunteerList.clear()
                if (snapshot.exists()) {
                    for (volunteerSnapshot in snapshot.children) {
                        val volunteer = volunteerSnapshot.getValue(VolunteerDataClass::class.java)
                        if (volunteer != null) {
                            // Set the key for each volunteer object
                            volunteer.key = volunteerSnapshot.key // Ensure you have a key field in VolunteerDataClass
                            volunteerList.add(volunteer)
                        }
                    }
                    setupAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("VolunteerManagement", "Database error: ${error.message}")
            }
        })
    }

    private fun setupAdapter() {
        volunteerAdapter = VolunteerAdapter(volunteerList) { volunteer, action ->
            when (action) {
                "edit" -> editVolunteer(volunteer)
                "delete" -> deleteVolunteer(volunteer)
            }
        }
        recyclerView.adapter = volunteerAdapter
    }

    private fun editVolunteer(volunteer: VolunteerDataClass) {
        val intent = Intent(this, EditVolunteer::class.java).apply {
            putExtra("VOLUNTEER_KEY", volunteer.key) // Pass the volunteer key
            putExtra("FIRST_NAME", volunteer.FirstName)
            putExtra("EMAIL", volunteer.Email)
            putExtra("PHONE_NUMBER", volunteer.PhoneNumber) // Pass additional details if needed
        }
        startActivity(intent)
    }

    private fun deleteVolunteer(volunteer: VolunteerDataClass) {
        // Create an AlertDialog to confirm deletion
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Volunteer")
        builder.setMessage("Are you sure you want to delete ${volunteer.FirstName}?")

        // Set positive button action
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Access the Firebase database reference
            val database = FirebaseDatabase.getInstance().getReference("mediashare")

            // Check if the volunteer has a valid key
            volunteer.key?.let { key ->
                database.child(key).removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Volunteer deleted successfully", Toast.LENGTH_SHORT).show()
                        fetchVolunteers() // Refresh the list after deletion
                    } else {
                        Toast.makeText(this, "Failed to delete volunteer: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, "No valid key found for deletion", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss() // Dismiss the dialog
        }

        // Set negative button action
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog without doing anything
        }

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

}