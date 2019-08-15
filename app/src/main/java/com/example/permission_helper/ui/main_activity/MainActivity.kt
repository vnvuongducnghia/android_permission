package com.example.permission_helper.ui.main_activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.permission_helper.R
import com.example.permission_helper.ui.BaseActivity
import com.example.permission_helper.ui.BaseFragment
import com.example.permission_helper.ui.demo_recycler_view.RecyclerFragment
import com.example.testrecyclerviewdt.helper.PermissionHelper


class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.name

    private lateinit var mPermissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check permission
        mPermissionHelper = PermissionHelper(this)
        mPermissionHelper.setOnPermissionListener(object : PermissionHelper.PermissionListener {
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

        // mPermissionHelper.checkPermission(PermissionHelper.PermissionType.CAMERA)
        BaseFragment.addFragment(this, RecyclerFragment())

    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.findFragmentById(R.id.flContent) == null) {
            BaseFragment.addFragment(this, RecyclerFragment())
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
