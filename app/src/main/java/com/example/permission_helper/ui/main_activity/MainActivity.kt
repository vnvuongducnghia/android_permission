package com.example.permission_helper.ui.main_activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.permission_helper.R
import com.example.testrecyclerviewdt.helper.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    private lateinit var mPermissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check permission
        mPermissionHelper = PermissionHelper(this)
        mPermissionHelper.setOnPermissionListener(object : PermissionHelper.OnPermissionListener {
            override fun onGranted(currentType: PermissionHelper.PermissionType?) {
                println("MainActivity.onGranted tat ca da duoc dang ky.")
            }

            override fun onDenied() {
                println("MainActivity.onDenied Da tu choi")
            }

            override fun onCustomDialog() {
                val activity = mPermissionHelper.getActivity()
                if (activity != null) {
                    val builder = AlertDialog.Builder(activity)
                    builder.setMessage("Open Setting")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                            mPermissionHelper.showSetting()
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                            dialog.dismiss()
                        })
                    builder.create().show()
                }
            }
        })

        txtHelloWorld.setOnClickListener {
            mPermissionHelper.checkPermission(PermissionHelper.PermissionType.CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionHelper.checkResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
