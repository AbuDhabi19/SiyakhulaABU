package com.example.siyakhula

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class EditProgram : AppCompatActivity() {
    private lateinit var programNameEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var program: Program
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_program)

        programNameEditText = findViewById(R.id.et_program_name)
        updateButton = findViewById(R.id.btn_update_program)

        // Get the program data from the intent
        program = intent.getParcelableExtra("program") ?: return

        // Populate the fields with the current program details
        programNameEditText.setText(program.name)

        updateButton.setOnClickListener {
            val updatedName = programNameEditText.text.toString()
            if (updatedName.isNotEmpty()) {
                updateProgramInFirestore(updatedName)
                startActivity(Intent(this, ProgramManagement::class.java))
            } else {
                Toast.makeText(this, "Program name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProgramInFirestore(updatedName: String) {
        val programRef = db.collection("programs").document(program.id)
        programRef.update("name", updatedName)
            .addOnSuccessListener {
                Toast.makeText(this, "Program updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity and return to the previous one
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error updating program", Toast.LENGTH_SHORT).show()
            }
    }
}
