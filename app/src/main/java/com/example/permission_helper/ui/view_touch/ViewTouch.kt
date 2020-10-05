package com.example.permission_helper.ui.view_touch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import com.example.permission_helper.R
import kotlinx.android.synthetic.main.activity_view_touch.*


class ViewTouch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_touch)


        flClick.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    println("ViewTouch flA ACTION_DOWN")
                }
                MotionEvent.ACTION_MOVE -> {
                    println("ViewTouch flA ACTION_MOVE")
                }
                MotionEvent.ACTION_UP -> {
                    println("ViewTouch flA ACTION_UP")
                }
            }
            super.onTouchEvent(event)
        }

        flClick.setOnClickListener {
            println("ViewTouch flA onClick")
        }

        btnA.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    println("ViewTouch btnA ACTION_DOWN")
                    return@setOnTouchListener false
                }
            }
            super.onTouchEvent(event)
        }


        /*flB.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    println("ViewTouch flB ACTION_DOWN")
                }

                MotionEvent.ACTION_UP -> {
                    println("ViewTouch flB ACTION_UP")
                }
            }
            false
        }*/

//        flB.setOnClickListener {
//            println("ViewTouch flB onClick")
//        }


    }
}
