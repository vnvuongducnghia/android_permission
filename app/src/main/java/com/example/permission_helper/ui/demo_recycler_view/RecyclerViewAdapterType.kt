package com.example.permission_helper.ui.demo_recycler_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.permission_helper.R
import com.example.permission_helper.ui.demo_recycler_view.view_holder_type.AvatarViewHolder
import com.example.permission_helper.ui.demo_recycler_view.view_holder_type.ContactViewHolder

class RecyclerViewAdapterType(var dataAny: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val CONTACT = 0
        private const val IMAGE = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        when (position) {
            CONTACT -> viewHolder =
                ContactViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.contact_view_holder, p0, false))
            else -> viewHolder =
                AvatarViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.avatar_view_holder, p0, false))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataAny.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        when (p0.itemViewType) {
            CONTACT -> {
                setDataContact(p0 as ContactViewHolder, position)
            }
            else -> {
                setDataAvatar(p0 as AvatarViewHolder, position)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (dataAny[position] is Contact) {
            return CONTACT
        } else if (dataAny[position] is String) {
            return IMAGE
        }
        return -1
    }

    /*Set contact*/
    private fun setDataContact(view: ContactViewHolder, p1: Int) {
        // Get the data model based on position
        val contact = dataAny[p1] as Contact

        // value first
        contact.firstItemInSection = p1 == 0

        // Set component item click
        val textView = view.txtName
        textView.text = contact.name
        /*textView.setOnClickListener {
            mOnClickListener.onItemClick(textView, p1)
        }*/

        val button = view.btnMessage
        button.text = if (contact.isOnline) "Message" else "Offline"
        button.isEnabled = contact.isOnline
        /*button.setOnClickListener {
            mOnClickListener.onItemClick(button, p1)
        }
        button.setOnLongClickListener {
            mOnClickListener.onItemLongClick(button, p1)
            true
        }*/
    }

    /*Set data avatar*/
    private fun setDataAvatar(view: AvatarViewHolder, position: Int) {
        val avatar = view.avatar
        avatar.setImageResource(R.drawable.avatar)
        // avatar.setOnClickListener { mOnClickListener.onItemClick(avatar, position) }
    }

    /*Get item*/
    fun getItem(p1: Int): Any {
        return dataAny[p1]
    }

    /*private lateinit var mOnClickListener: OnClickListener

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.mOnClickListener = onClickListener
    }

    interface OnClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int) {}
    }*/
}