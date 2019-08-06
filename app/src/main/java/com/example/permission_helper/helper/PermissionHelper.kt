package com.example.testrecyclerviewdt.helper

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * ActivityCompat.requestPermissions bên trong đã có vòng lặp for nên từng dialog request sẽ hiện.
 */
class PermissionHelper {

    /* Init Permission helper --- */
    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null
    private var mOnPermissionListener: OnPermissionListener? = null
    private var mCurrentType: PermissionType? = null
    private var mSharedPre: SharedPreferences? = null
    private val mPackageName: String
        get() {
            return getActivity()?.packageName ?: ""
        }

    companion object {
        const val REQUEST_CODE_ASK_PERMISSIONS = 1122
        const val IS_FIRST_TIME_REQUEST_PERMISSION_CAMERA = "IS_FIRST_TIME_REQUEST_PERMISSION_CAMERA"
        const val IS_FIRST_TIME_REQUEST_PERMISSION_GALLERY = "IS_FIRST_TIME_REQUEST_PERMISSION_GALLERY"
        const val IS_FIRST_TIME_REQUEST_PERMISSION_READ_CONTACTS = "IS_FIRST_TIME_REQUEST_PERMISSION_READ_CONTACTS"
        const val IS_FIRST_TIME_REQUEST_PERMISSION_LOCATION = "IS_FIRST_TIME_REQUEST_PERMISSION_LOCATION"
        const val IS_FIRST_TIME_REQUEST_PERMISSION_CALL_PHONE = "IS_FIRST_TIME_REQUEST_PERMISSION_CALL_PHONE"
    }

    // -------------
    // Constructor
    // -------------

    constructor(activity: Activity) {
        mActivity = activity
        mSharedPre = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    constructor(fragment: Fragment) {
        mFragment = fragment
        mSharedPre = PreferenceManager.getDefaultSharedPreferences(fragment.activity)
    }

    // -------------
    // Top Methods
    // -------------

    fun setOnPermissionListener(onPermissionListener: OnPermissionListener) {
        mOnPermissionListener = onPermissionListener
    }

    fun checkPermission(type: PermissionType) {
        mCurrentType = type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = checkPermissions(type)
            if (!permissions.listNotGranted.isNullOrEmpty()) {
                println("PermissionHelper.checkPermission: Need permissions.......")
                val notYetGrants = permissions.listNotGranted

                println("PermissionHelper.checkPermission: Check list dialog rationale")
                if (!permissions.listRationale.isNullOrEmpty() && permissions.listNotGranted!!.size == permissions.listRationale!!.size) {
                    val preName = getNameType(type)
                    if (!mSharedPre!!.getBoolean(preName, false)) {
                        println("PermissionHelper.checkPermission: List dialog rationale not showing by first request: 1 deny, 2 allow")
                        mSharedPre!!.edit().putBoolean(preName, true).apply()
                        requestPermissions(notYetGrants!!.toTypedArray(), REQUEST_CODE_ASK_PERMISSIONS)
                    } else {
                        println("PermissionHelper.checkPermission: List dialog rationale not showing by all check Don't show again")

                        // message list Rationale
                        val listRationale = permissions.listRationale
                        val message = StringBuilder("You need to grant access to " + listRationale!![0])
                        val size = listRationale.size
                        for (i in 1 until size) {
                            message.append(", ").append(listRationale[i])
                        }

                        println("PermissionHelper.checkPermission: Go setting")
                        mOnPermissionListener!!.onCustomDialog() // dialogDefault(message.toString())

                    }
                } else {
                    println("PermissionHelper.checkPermission: DialogRationale showing...")
                    requestPermissions(notYetGrants!!.toTypedArray(), REQUEST_CODE_ASK_PERMISSIONS)
                }
            } else {
                if (mOnPermissionListener != null) {
                    mOnPermissionListener!!.onGranted(mCurrentType)
                }
            }
        } else {
            if (mOnPermissionListener != null) {
                mOnPermissionListener!!.onGranted(mCurrentType)
            }
        }
    }

    /**
     * Use at onRequestPermissionsResult in Activity
     */
    fun checkResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        println("PermissionHelper.checkResult: requestCode $requestCode")
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {

                val permissionsString = getPermissionsStringByType(mCurrentType)
                val size = permissionsString.size
                val perms = HashMap<String, Int>()

                // Initial
                for (i in 0 until size) {
                    perms[permissionsString[i]] = PackageManager.PERMISSION_GRANTED
                }

                // Fill with results
                val len = permissions.size
                for (i in 0 until len) {
                    perms[permissions[i]] = grantResults[i]
                }

                for (i in 0 until size) {
                    if (perms[permissionsString[i]] != PackageManager.PERMISSION_GRANTED) {
                        if (mOnPermissionListener != null) {
                            println("PermissionHelper.checkResult: Permission Denied")
                            mOnPermissionListener!!.onDenied()
                        }
                        return
                    }
                }

                if (mOnPermissionListener != null) {
                    println("PermissionHelper.checkResult: All Permissions Granted")
                    mOnPermissionListener!!.onGranted(mCurrentType)
                }
            }
        }
    }

    fun getActivity(): Activity? {
        if (mActivity != null) {
            return mActivity
        } else if (mFragment != null) {
            return mFragment!!.activity
        }
        return null
    }

    /**
     * Check permissions and after return object Permissions
     */
    private fun checkPermissions(type: PermissionType): Permissions {
        println("PermissionHelper.checkPermissions: Check start")
        val listNotGranted = ArrayList<String>()
        val listRationale = ArrayList<String>()
        when (type) {
            PermissionType.CAMERA -> {
                checkGranted(
                    listNotGranted, Manifest.permission.CAMERA,
                    listRationale, "Camera"
                )
                checkGranted(
                    listNotGranted, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    listRationale, "Write External Storage"
                )
                checkGranted(
                    listNotGranted, Manifest.permission.READ_EXTERNAL_STORAGE,
                    listRationale, "Read External Storage"
                )
            }
            PermissionType.GALLERY -> {
                checkGranted(
                    listNotGranted, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    listRationale, "Write External Storage"
                )
                checkGranted(
                    listNotGranted, Manifest.permission.READ_EXTERNAL_STORAGE,
                    listRationale, "Read External Storage"
                )
            }
            PermissionType.LOCATION -> {
                checkGranted(
                    listNotGranted, Manifest.permission.ACCESS_COARSE_LOCATION,
                    listRationale, "Location"
                )
            }
            PermissionType.READ_CONTACTS -> {
                checkGranted(
                    listNotGranted, Manifest.permission.READ_CONTACTS,
                    listRationale, "Contacts"
                )
            }
            PermissionType.CALL_PHONE -> {
                checkGranted(
                    listNotGranted, Manifest.permission.CALL_PHONE,
                    listRationale, "Phone"
                )
            }
        }

        val myPermissions = Permissions()
        myPermissions.listNotGranted = listNotGranted
        myPermissions.listRationale = listRationale
        println("PermissionHelper.checkPermissions: Check end")
        return myPermissions
    }

    private fun checkGranted(
        listNotGranted: MutableList<String>,
        permission: String,
        listRationale: MutableList<String>,
        message: String
    ) {
        val activity = getActivity()
        if (activity != null) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                println("PermissionHelper.checkGranted: Add $permission")
                listNotGranted.add(permission)

                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    println("PermissionHelper.checkGranted: Not show dialog Rational. Have 2 case: 1: First request, 2: Don't ask again")
                    listRationale.add(message)
                }
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        println("PermissionHelper.requestPermissions: requestCode $requestCode")
        if (mActivity != null) {
            ActivityCompat.requestPermissions(mActivity!!, permissions, requestCode)
        } else {
            mFragment?.requestPermissions(permissions, requestCode)
        }
    }

    private fun getNameType(type: PermissionType): String {
        return when (type) {
            PermissionType.CAMERA -> IS_FIRST_TIME_REQUEST_PERMISSION_CAMERA
            PermissionType.GALLERY -> IS_FIRST_TIME_REQUEST_PERMISSION_GALLERY
            PermissionType.READ_CONTACTS -> IS_FIRST_TIME_REQUEST_PERMISSION_READ_CONTACTS
            PermissionType.LOCATION -> IS_FIRST_TIME_REQUEST_PERMISSION_LOCATION
            PermissionType.CALL_PHONE -> IS_FIRST_TIME_REQUEST_PERMISSION_CALL_PHONE
        }
    }

    private fun startActivityForResult(intent: Intent, requestCode: Int) {
        mActivity?.startActivityForResult(intent, requestCode)
            ?: mFragment?.startActivityForResult(intent, requestCode)
    }

    private fun getPermissionsStringByType(type: PermissionType?): List<String> {
        val permissions = java.util.ArrayList<String>()
        if (type != null) {
            println("PermissionHelper.getPermissionsStringByType: $type")
            when (type) {
                PermissionType.CAMERA -> {
                    permissions.add(Manifest.permission.CAMERA)
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                PermissionType.GALLERY -> {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                PermissionType.READ_CONTACTS -> permissions.add(Manifest.permission.READ_CONTACTS)
                PermissionType.LOCATION -> permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
                PermissionType.CALL_PHONE -> permissions.add(Manifest.permission.CALL_PHONE)
            }
        }
        return permissions
    }

    private fun dialogDefault(message: String) {
        val builder = AlertDialog.Builder(this.mActivity!!)
        builder.setMessage(message)
            .setPositiveButton("fire", DialogInterface.OnClickListener { dialog, id ->
                showSetting()
            })
            .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss() // User cancelled the dialog
            })
        builder.create().show()
    }

    fun showSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", mPackageName, null)
        intent.data = uri
        startActivityForResult(intent, REQUEST_CODE_ASK_PERMISSIONS)
    }

    // -------------
    // Class
    // -------------

    enum class PermissionType {
        CAMERA, GALLERY, READ_CONTACTS, LOCATION, CALL_PHONE;
    }

    interface OnPermissionListener {
        fun onGranted(currentType: PermissionType?)
        fun onDenied()
        fun onCustomDialog()
    }

    inner class Permissions {
        var listNotGranted: List<String>? = null
        var listRationale: List<String>? = null
    }
}