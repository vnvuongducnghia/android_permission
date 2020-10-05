package com.example.permission_helper.ui.dialog

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import com.example.permission_helper.ui.BaseActivity

abstract class BaseDialogNew : DialogFragment() {

    private var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
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

    override fun show(fm: FragmentManager, tag: String?) {
        super.show(fm, tag)
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