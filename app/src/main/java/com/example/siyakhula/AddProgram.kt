package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddProgram : AppCompatActivity() {
    private lateinit var programNameEditText: EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_program)
        programNameEditText = findViewById(R.id.et_program_name)
        val saveButton: Button = findViewById(R.id.AddProgramBTN)

    }

    fun returnClick(view: View) {
        // Redirect to Add Program
        startActivity(Intent(this, ProgramManagement::class.java))
    }
    fun ConfirmProgBTNClick(view: View) {
        val programName = programNameEditText.text.toString()
        if (programName.isNotEmpty()) {
            saveProgram(programName)
            startActivity(Intent(this, ProgramManagement::class.java))
        } else {
            Toast.makeText(this, "Please enter a program name", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveProgram(programName: String) {
        val newProgram = Program(name = programName)
        db.collection("programs").add(newProgram).addOnSuccessListener {
            Toast.makeText(this, "Program added", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to add program", Toast.LENGTH_SHORT).show()
        }
    }
}