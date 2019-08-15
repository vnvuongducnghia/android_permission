package com.example.permission_helper.ui.main_splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.permission_helper.R
import com.example.permission_helper.ui.BaseActivity
import com.example.permission_helper.ui.main_activity.MainActivity

class Splash : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        timeWait(1000L)
    }

    private fun timeWait(millisecond: Long) {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, millisecond)
    }
}
