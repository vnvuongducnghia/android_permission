package com.example.permission_helper.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import com.example.permission_helper.R
import com.example.testrecyclerviewdt.util.ScreenUtils
import kotlinx.android.synthetic.main.dialog_alert.*

/**
 * Created by nghia.vuong on 10,February,2020
 */
typealias OnPositive = () -> Unit

typealias OnNegative = () -> Unit

typealias OnDismiss = () -> Unit

class DialogAlert : BaseDialog() {

    private val TAG = DialogAlert::class.java.simpleName

    private var title: String?= null
    private var message: String?= null
    private var titleNegative: String?= null
    private var titlePositive: String = "OK"

    private var onNegative: OnNegative? = null
    private var onPositive: OnPositive? = null
    private var onDismiss: OnDismiss? = null

    fun show(context: Context) {
        super.show(context, TAG)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!, R.style.fullScreenDialog)

        // Set window layout
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawableResource(R.drawable.shape_dialog_alert_background)
            dialog.window!!.attributes.dimAmount = 0.5f
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            val sw = ScreenUtils.getScreenWidth(context!!)
            val sh = ScreenUtils.getScreenHeight(context!!)
            val size = if (sw < sh) sw else sh
            dialog.window!!.setLayout((size * 0.855).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // Set window key
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }

        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.dialog_alert, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (title.isNullOrEmpty()) {
            txtTitle.visibility = View.GONE
        }else{
            txtTitle.visibility = View.VISIBLE
            txtTitle.text = title
        }

        if (message.isNullOrEmpty()) {
            txtMessage.visibility = View.GONE
        }else{
            txtMessage.visibility = View.VISIBLE
            txtMessage.text = message
        }

        if (titleNegative.isNullOrEmpty()) {
            btnNegative.visibility = View.GONE
        }else{
            btnNegative.visibility = View.VISIBLE
            btnNegative.text = titleNegative
            btnNegative.setOnClickListener {
                onNegative?.invoke()
            }
        }

        btnPositive.text = titlePositive
        btnPositive.setOnClickListener {
            onPositive?.invoke()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

    fun setTitle(title: String): DialogAlert {
        this.title = title
        return this
    }

    fun setMessage(message: String): DialogAlert {
        this.message = message
        return this
    }

    fun setTitlePositive(positive: String): DialogAlert {
        this.titlePositive = positive
        return this
    }

    fun setTitleNegative(negative: String): DialogAlert {
        this.titleNegative = negative
        return this
    }

    fun onPositive(onPositive: OnPositive?): DialogAlert {
        this.onPositive = onPositive
        return this
    }

    fun onNegative(onNegative: OnNegative?): DialogAlert {
        this.onNegative = onNegative
        return this
    }

    fun onDismiss(onDismiss: OnDismiss?): DialogAlert {
        this.onDismiss = onDismiss
        return this
    }


}