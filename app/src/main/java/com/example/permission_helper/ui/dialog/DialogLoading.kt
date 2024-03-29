package com.example.permission_helper.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.example.permission_helper.R
import kotlinx.android.synthetic.main.dialog_loading.*


class DialogLoading : BaseDialog() {

    private var startedShowing = false
    private var mStartMillisecond = 0L
    private var mStopMillisecond = 0L
    private val mDelayMillisecond = 100
    private var onCancelListener: DialogInterface.OnCancelListener? = null
    private val SHOW_MIN_MILLISECOND = 500


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // disable cancel
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        // dialog.setOnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK }
        if (dialog.window != null) {
            dialog.window!!.attributes.dimAmount = 0.5f
            dialog.window!!.setBackgroundDrawableResource(R.drawable.background_transparent)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivLoading?.post { (ivLoading?.drawable as AnimationDrawable?)?.start() }
    }

    /**
     * Show
     */

    fun isShowing(): Boolean {
        return startedShowing
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        mStartMillisecond = System.currentTimeMillis()
        startedShowing = false
        mStopMillisecond = java.lang.Long.MAX_VALUE
        Handler().postDelayed({
            if (mStopMillisecond > System.currentTimeMillis())
                showDialogAfterDelay(manager, tag)
        }, mDelayMillisecond.toLong())
    }

    private fun showDialogAfterDelay(fm: FragmentManager?, tag: String?) {
        try {
            startedShowing = true
            if (fm != null) {
                super.show(fm, tag)
            }
        } catch (e: IllegalStateException) {
            e.message?.let { Log.e("Error", it) }
        }
    }

    /**
     * Cancel
     */

    fun cancel() {
        mStopMillisecond = System.currentTimeMillis()
        if (startedShowing) {
            if (ivLoading != null) {
                cancelWhenShowing()
            } else {
                cancelWhenNotShowing()
            }
        }

    }

    private fun cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + mDelayMillisecond.toLong() + SHOW_MIN_MILLISECOND.toLong()) {
            Handler().postDelayed({
                try {
                    dismissAllowingStateLoss()
                } catch (e: NullPointerException) {
                    if (e.message != null) e.message else ""?.let { Log.e("Error", it) }
                }
            }, SHOW_MIN_MILLISECOND.toLong())
        } else {
            try {
                dismissAllowingStateLoss()
            } catch (e: NullPointerException) {
                if (e.message != null) e.message else ""?.let { Log.e("Error", it) }
            }
        }
    }

    private fun cancelWhenNotShowing() {
        Handler().postDelayed({
            try {
                dismissAllowingStateLoss()
            } catch (e: NullPointerException) {
                if (e.message != null) e.message else ""?.let { Log.e("Error", it) }
                dismiss()
            }
        }, mDelayMillisecond.toLong())
    }

}