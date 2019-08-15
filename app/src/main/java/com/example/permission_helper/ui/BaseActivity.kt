package com.example.permission_helper.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.permission_helper.ui.dialog.DialogLoading
import com.example.testrecyclerviewdt.util.NetworkUtils

abstract class BaseActivity : AppCompatActivity(), BaseFragment.Callback {

    /**
     * Variable
     */
    var isAlive = false
    var isNetworkConnected: Boolean = false
    var isTouchDisable: Boolean = false
    var isDisableDispatchTouchEvent: Boolean = false

    private var mLoading: DialogLoading? = null

    /**
     * Override
     */

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkConnected = NetworkUtils.isNetworkConnected(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isAlive = true
    }

    override fun onPause() {
        super.onPause()
        isAlive = false
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !isDisableDispatchTouchEvent) {
            val view = currentFocus
            if (view != null && (view is AppCompatEditText || view is EditText)) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        return isTouchDisable || super.dispatchTouchEvent(event)
    }

    /**
     * DialogLoading
     */

    fun hideLoading() {
        mLoading?.dismiss()
    }

    fun showLoading() {
        hideLoading()
        if (mLoading == null) {
            mLoading = DialogLoading()
        }
        mLoading?.show(this, "dialog_loading")
    }

    /**
     * Keyboard
     */

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 2)
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Disposable
     */



    /**
     * Fragment listener
     */

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

}