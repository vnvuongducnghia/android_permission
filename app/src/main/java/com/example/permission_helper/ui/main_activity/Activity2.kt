package com.example.permission_helper.ui.main_activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.permission_helper.R
import kotlinx.android.synthetic.main.activity_2.*

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val intent = intent
        val abc = intent.getStringExtra("name_abc")
        txtResult.text = "name_abc $abc"

        btnBack.setOnClickListener {
            // Send back data
            val intent = Intent()
            intent.putExtra("activity2_123", 300)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

}
