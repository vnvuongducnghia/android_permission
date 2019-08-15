package com.example.permission_helper.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle


class DialogExAlertDialog : BaseDialog() {

    companion object {
        fun getInstance(title: String): DialogExAlertDialog {
            val dialog = DialogExAlertDialog()
            val bundle = Bundle()
            bundle.putString("title", title);
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments!!.getString("title")
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage("Are you sure?")
        alertDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // on success
        })
        alertDialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            if (dialog != null) {
                dialog.dismiss()
            }
        })
        return alertDialogBuilder.create()
    }
}