package com.example.permission_helper.ui.dialog

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import com.example.permission_helper.R
import kotlinx.android.synthetic.main.dialog_loading.*


class DialogLoadingProgress : BaseDialog() {

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
            dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide Image
        ivLoading.visibility = View.GONE

        // Show ProgressBar.
        progressBar.visibility = View.VISIBLE
        val anim = ValueAnimator.ofFloat(0f, 100f)
        anim.addUpdateListener { valueAnimator ->
            val slideOffset = valueAnimator.animatedValue as Float
            val slideOffsetInt = slideOffset.toInt()
            progressBar.progress = slideOffsetInt
            if (slideOffsetInt == 100) {
                Handler().postDelayed({ dismiss() }, 500)
            }
        }
        anim.interpolator = DecelerateInterpolator()
        anim.duration = 5000
        anim.start()
    }

    /**
     * Show
     */

    fun isShowing(): Boolean {
        return startedShowing
    }

    fun show(context: Context?) {
        startedShowing = true
        val tag = "dialog_loading_progress"
        show(context, tag)
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
                    Log.e("Error", if (e.message != null) e.message else "")
                }
            }, SHOW_MIN_MILLISECOND.toLong())
        } else {
            try {
                dismissAllowingStateLoss()
            } catch (e: NullPointerException) {
                Log.e("Error", if (e.message != null) e.message else "")
            }
        }
    }

    private fun cancelWhenNotShowing() {
        Handler().postDelayed({
            try {
                dismissAllowingStateLoss()
            } catch (e: NullPointerException) {
                Log.e("Error", if (e.message != null) e.message else "")
                dismiss()
            }
        }, mDelayMillisecond.toLong())
    }

}