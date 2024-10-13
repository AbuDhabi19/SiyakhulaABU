package com.example.siyakhula

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VolunteerAdapter(
    private val volunteerList: List<VolunteerDataClass>,
    private val onActionClick: (VolunteerDataClass, String) -> Unit
) : RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder>() {

    inner class VolunteerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstNameTextView: TextView = view.findViewById(R.id.firstNameTextView)
        val editButton: TextView = view.findViewById(R.id.editButton)
        val deleteButton: TextView = view.findViewById(R.id.deleteButton)

        fun bind(volunteer: VolunteerDataClass) {
            firstNameTextView.text = volunteer.FirstName

            editButton.setOnClickListener {
                onActionClick(volunteer, "edit")
            }
            deleteButton.setOnClickListener {
                onActionClick(volunteer, "delete")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.volunteer_item_layout, parent, false)
        return VolunteerViewHolder(view)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        holder.bind(volunteerList[position])
    }

    override fun getItemCount(): Int = volunteerList.size
}