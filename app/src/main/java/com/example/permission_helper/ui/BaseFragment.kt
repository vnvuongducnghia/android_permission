package com.example.permission_helper.ui

class BaseFragment {

    /* Class */
    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}