package com.example.permission_helper.ui.demo_recycler_view.view_holder_type

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.permission_helper.R

class AvatarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var avatar: ImageView = itemView.findViewById(R.id.avatar)
}