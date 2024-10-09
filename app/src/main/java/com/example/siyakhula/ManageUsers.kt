package com.example.siyakhula

import UserAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.firestore.FirebaseFirestore

class ManageUsers : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val usersList = mutableListOf<User>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manageusers)

        // Initialize RecyclerView with LinearLayoutManager
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize UserAdapter
        userAdapter = UserAdapter(usersList) { user, action ->
            when (action) {
                "edit" -> editUser(user)
                "delete" -> deleteUser(user)
            }
        }
        userRecyclerView.adapter = userAdapter

        // Add the DividerItemDecoration
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        userRecyclerView.addItemDecoration(dividerItemDecoration)

        // Fetch users from Firestore
        fetchUsers()
    }

    fun AddUserBTNClick(view: View) {
        val intent = Intent(this, AddUser::class.java)
        startActivity(intent)
    }

    private fun fetchUsers() {
        db.collection("Users").get().addOnSuccessListener { result ->
            usersList.clear()
            for (document in result) {
                val user = document.toObject(User::class.java).apply {
                    id = document.id

                }
                usersList.add(user)
            }
            userAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error fetching users: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editUser(user: User) {
        // Create an Intent to start the EditUser activity
        val intent = Intent(this, EditUser::class.java)
        // Pass the user ID to the EditUser activity
        intent.putExtra("USER_ID", user.id)
        startActivity(intent)
    }

    private fun deleteUser(user: User) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete User")
        builder.setMessage("Are you sure you want to delete this user?")
        builder.setPositiveButton("Yes") { _, _ ->
            db.collection("Users").document(user.id).delete().addOnSuccessListener {
                usersList.remove(user)
                userAdapter.notifyDataSetChanged()
                Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error deleting user", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun returnClick(view: View) {
        // Redirect to Admin Dashboard
        startActivity(Intent(this, AdminDashboard::class.java))
    }
}
