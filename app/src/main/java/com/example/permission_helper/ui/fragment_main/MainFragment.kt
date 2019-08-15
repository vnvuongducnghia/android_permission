package com.example.permission_helper.ui.fragment_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.permission_helper.R
import com.example.permission_helper.ui.BaseFragment
import com.example.permission_helper.ui.demo_recycler_view.Contact
import com.example.permission_helper.ui.dialog.DialogExAlertDialog
import com.example.permission_helper.ui.dialog.DialogLoadingProgress
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private lateinit var contacts: ArrayList<Contact>
    private var listAny: ArrayList<Any> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init data
        contacts = Contact.createContactsList(20)

        // Initialize anyonItemClick
        listAny.addAll(Contact.createContactsList(2))
        listAny.add("image")
        listAny.add("image")
        listAny.addAll(Contact.createContactsList(3))
        listAny.add("image")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testDialogFragment.setOnClickListener {
            // showLoading()
            // DialogExStyle().show(this, "")
            DialogLoadingProgress().show(view.context)
        }

        testDialogFragment2.setOnClickListener {
            DialogExAlertDialog.getInstance("Nghia").show(view.context, "fragment_alert")
        }
    }
}
