package com.example.siyakhula

import ProgramAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProgramManagement : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var programAdapter: ProgramAdapter
    private val programsList = mutableListOf<Program>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_program_management)

        // Initialize RecyclerView
//        recyclerView = findViewById(R.id.recycler_view_programs)
//        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize RecyclerView with GridLayoutManager
                recyclerView = findViewById(R.id.recycler_view_programs)
        val gridLayoutManager = GridLayoutManager(this, 1) // 2 columns in the grid
        recyclerView.layoutManager = gridLayoutManager
        // Set ItemDecoration for grid spacing
        recyclerView.addItemDecoration(GridItemDecoration(16)) // Use spacing as needed


        programAdapter = ProgramAdapter(programsList) { program, action ->
            when (action) {
                "edit" -> editProgram(program)
                "delete" -> deleteProgram(program)
            }
        }
        recyclerView.adapter = programAdapter

        // Fetch programs from Firestore
        fetchPrograms()
    }


    fun AddProgBTNClick(view: View) {
        // Redirect to Add Program
        startActivity(Intent(this, AddProgram::class.java))
    }

    fun returnClick(view: View) {
        // Redirect to Admin Dashboard
        startActivity(Intent(this, AdminDashboard::class.java))
    }

    private fun fetchPrograms() {
        db.collection("programs").get().addOnSuccessListener { result ->
            programsList.clear()
            for (document in result) {
                val program = document.toObject(Program::class.java).apply {
                    id = document.id
                }
                programsList.add(program)
            }
            programAdapter.notifyDataSetChanged()
        }
    }

    private fun editProgram(program: Program) {
        // Logic to handle editing a program (open another activity or dialog)
    }

//    private fun deleteProgram(program: Program) {
//        db.collection("programs").document(program.id).delete().addOnSuccessListener {
//            programsList.remove(program)
//            programAdapter.notifyDataSetChanged()
//            Toast.makeText(this, "Program deleted", Toast.LENGTH_SHORT).show()
//        }
//    }
private fun deleteProgram(program: Program) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Delete Program")
    builder.setMessage("Are you sure you want to delete this program?")
    builder.setPositiveButton("Yes") { _, _ ->
        db.collection("programs").document(program.id).delete().addOnSuccessListener {
            programsList.remove(program)
            programAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Program deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Error deleting program", Toast.LENGTH_SHORT).show()
        }
    }
    builder.setNegativeButton("No") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

}