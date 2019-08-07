package com.example.permission_helper.ui._dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import com.example.permission_helper.R
import com.example.testrecyclerviewdt.util.ScreenUtils

class DialogExStyle:BaseDialog() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!, R.style.DialogTheme)
        val root = RelativeLayout(activity)
        root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawableResource(R.drawable.background_white)
            dialog.window!!.attributes.dimAmount = 0.5f
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            val sw = ScreenUtils.getScreenWidth(context!!)
            val sh = ScreenUtils.getScreenHeight(context!!)
            val size = if (sw < sh) sw else sh
            dialog.window!!.setLayout((size * 0.855).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return dialog
    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.dialog_loading_green, container, false);
//    }
}