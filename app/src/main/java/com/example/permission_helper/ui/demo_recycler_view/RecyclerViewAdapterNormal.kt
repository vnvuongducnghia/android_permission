package com.example.permission_helper.ui.demo_recycler_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permission_helper.R
import com.example.permission_helper.ui.demo_recycler_view.view_holder_type.ContactViewHolder

class RecyclerViewAdapterNormal(var data: List<Contact>) : RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): ContactViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.contact_view_holder, p0, false)
//        // Set item click
//        view.setOnClickListener {
//            mOnClickListener.onItemClick(view, position)
//        }
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ContactViewHolder, position: Int) {
        val contact = data[position]
        contact.firstItemInSection =  position == 0

        /*Set component item click*/

        // Name data
        val txtName = p0.txtName
        txtName.text = contact.name
//        // Name click
//        txtName.setOnClickListener {
//            mOnClickListener.onItemClick(txtName, position)
//        }

        // Message data
        p0.btnMessage.text = if (contact.isOnline) "Message" else "Offline"
        p0.btnMessage.isEnabled = contact.isOnline
//        // Message click
//        p0.btnMessage.setOnClickListener {
//            mOnClickListener.onItemClick(p0.btnMessage, position)
//        }
//        // Message long click
//        p0.btnMessage.setOnLongClickListener {
//            mOnClickListener.onItemLongClick(p0.btnMessage, position)
//            true
//        }


    }

    /**
     * Top Method
     */
    fun getItem(p1: Int): Contact {
        return data[p1]
    }

    /**
     * On click listener
     */
    interface OnClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int) {}
    }

    /*Set onClickListener*/
    private lateinit var mOnClickListener: OnClickListener

    fun setOnClickListener(clickListener: OnClickListener) {
        mOnClickListener = clickListener
    }

}