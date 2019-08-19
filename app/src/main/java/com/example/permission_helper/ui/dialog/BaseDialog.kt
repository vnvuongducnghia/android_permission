package com.example.permission_helper.ui.dialog

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.permission_helper.ui.BaseActivity

abstract class BaseDialog() : DialogFragment() {

    private var baseActivity: BaseActivity? = null

    /**
     * Attach
     */

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val mActivity = context as BaseActivity?
            this.baseActivity = mActivity
            mActivity!!.onFragmentAttached()
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    /**
     * Show
     */

    override fun show(fm: FragmentManager?, tag: String?) {
        val transaction = fm?.beginTransaction()
        val prevFragment = fm?.findFragmentByTag(tag)
        if (transaction != null) {
            if (prevFragment != null) {
                transaction.remove(prevFragment)
            }
            transaction.addToBackStack(null)
            show(transaction, tag)
        }
    }

    fun show(context: Context?, tag: String) {
        if (context != null && context is AppCompatActivity) {
            show(context.supportFragmentManager, tag) // "")
        }
    }

    /**
     * DialogLoading
     */

    fun showLoading() {
        baseActivity?.showLoading()
    }

    fun hideLoading() {
        baseActivity?.hideLoading()
    }

    /**
     * Keyboard
     */

    fun showKeyboard() {
        baseActivity?.showKeyboard()
    }

    fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }
}