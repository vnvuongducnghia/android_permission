package com.example.permission_helper.ui._dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.permission_helper.R


class DialogExCustomView : BaseDialog() {

    companion object{
        fun getInstance(data: String): DialogExCustomView {
            val dialog = DialogExCustomView()
            val bundle = Bundle()

            dialog.arguments = bundle
            return dialog
        }
    }

    // ------------
    // Override
    // ------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("DialogExCustomView.onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        println("DialogExCustomView.onCreateDialog")
        // điều duy nhất bạn override method này là khi bạn sử dụng onCreateView()
        // để chỉnh sửa dialog. Ví dụ, dialog bao gồm một title mặc định và bạn custom layout để xóa nó đi.
        // NHƯNG bạn phải phải gọi superclass để lấy Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Chúng ta có thể set layout ở đây, nhưng nó sẽ bị ghi đè ở onCreateView()
        dialog.setContentView(R.layout.dialog_loading_red)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("DialogExCustomView.onCreateView")
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("DialogExCustomView.onViewCreated: It not call if onCreateView return null")
    }



}