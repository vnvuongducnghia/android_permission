package com.example.permission_helper.ui.main_activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.permission_helper.R
import com.example.permission_helper.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.name

    val layoutParam = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDataToActivity = Button(this)
        btnDataToActivity.layoutParams = layoutParam
        btnDataToActivity.text = "Send Data To Activity"
        btnDataToActivity.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            intent.putExtra("name_abc", "Name ABC")
            startActivity(intent)
        }
        listButton.addView(btnDataToActivity)

        val btnResultActivity = Button(this)
        btnResultActivity.layoutParams = layoutParam
        btnResultActivity.text = "Activity result"
        btnResultActivity.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            val bundle = Bundle()
            bundle.putInt("name_123", 123456)
            intent.putExtra("bundle_123", bundle)
            startActivityForResult(intent, 111)
        }
        listButton.addView(btnResultActivity)

        val btnImplicitIntents = Button(this)
        btnImplicitIntents.layoutParams = layoutParam
        btnImplicitIntents.text = "ImplicitIntents"
        btnImplicitIntents.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.google.com")
            startActivity(intent)
        }
        listButton.addView(btnImplicitIntents)

        val btnPermissionActivity = Button(this)
        btnPermissionActivity.layoutParams = layoutParam
        btnPermissionActivity.text = "Permission Activity"
        btnPermissionActivity.setOnClickListener {
//            val intent = Intent(this, PermissionLibraryActivity::class.java)
//            startActivity(intent)
        }
        listButton.addView(btnPermissionActivity)
    }

    override fun onResume() {
        super.onResume()
        println("MainActivity.onResume")
    }

    override fun onStop() {
        super.onStop()
        println("MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MainActivity.onDestroy")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111) {
            if (data != null) {
                Toast.makeText(
                    this,
                    "data = ${data.getIntExtra("activity2_123", -1)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
