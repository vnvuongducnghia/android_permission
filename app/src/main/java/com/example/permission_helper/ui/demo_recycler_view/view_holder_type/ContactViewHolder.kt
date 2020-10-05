package com.example.permission_helper.ui.demo_recycler_view.view_holder_type

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.permission_helper.R

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtName: TextView = itemView.findViewById(R.id.contact_name)
    var btnMessage: Button = itemView.findViewById(R.id.message_button)
}