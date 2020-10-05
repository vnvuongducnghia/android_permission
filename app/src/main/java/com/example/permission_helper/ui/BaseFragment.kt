package com.example.permission_helper.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import android.view.View
import android.view.animation.Animation
import com.example.permission_helper.R
import com.example.permission_helper.ui.fragment_main.MainFragment
import com.example.permission_helper.ui.main_activity.MainActivity
import com.example.permission_helper.util.CommonUtils.TRANSITION_DELAY

abstract class BaseFragment : Fragment() {

    /**
     * Init variable
     */
    private var mBaseActivity: BaseActivity? = null
    private var mContext: Context? = null
    private var mLevel = 1
    var performClick: View? = null // use after login

    companion object {

        private var isSetFragmentInProcess = false
        private var isPopBackStack = false
        private const val LEVEL_1 = "Level1"

        /*Animation Fragment*/
        private var isDisableAnimation = false

        fun addFragment(activity: FragmentActivity?, fragment: BaseFragment) {
            if (activity == null) return

            // Get level
            var level = 1
            val fragmentCurrent = activity.supportFragmentManager?.findFragmentById(R.id.flContent)
            if (fragmentCurrent != null) {
                fragmentCurrent as BaseFragment
                level = fragmentCurrent.getLevel()
            }
            fragment.setLevel(level)

            setFragment(activity.supportFragmentManager, fragment)

        }

        private fun setFragment(fm: FragmentManager, f: BaseFragment, content: Int = R.id.flContent) {
            if (isSetFragmentInProcess) return
            isSetFragmentInProcess = true

            Handler().postDelayed({
                popBackStack(fm, f)
                val ft = fm.beginTransaction()
                if (f.getName() != LEVEL_1) {
                    // ft.setCustomAnimations(0, 0, 0, 0)
                } else {
                    // ft.setCustomAnimations(0, 0, 0, 0)
                }
                ft.replace(content, f)
                try {
                    ft.addToBackStack(f.getName()).commit()
                } catch (e: Exception) {
                }
                isPopBackStack = false
                isSetFragmentInProcess = false
            }, TRANSITION_DELAY)
        }

        @SuppressLint("CommitTransaction")
        private fun popBackStack(fm: FragmentManager, f: BaseFragment) {
            handlerPopBackStack(fm, f)
            if (fm.backStackEntryCount == 0) return
            try {
                if (isDisableAnimation) {
                    isDisableAnimation = true
                    fm.popBackStackImmediate(f.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    isDisableAnimation = false
                } else {
                    fm.popBackStackImmediate(f.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    if (f.getName() == LEVEL_1) {
                        fm.beginTransaction()
                    }
                }
            } catch (e: Exception) {
            }
        }

        // Avoid reloading parent when try to replace same level fragment. df: destinationFragment
        private fun handlerPopBackStack(fm: FragmentManager, df: BaseFragment) {
            try {
                val level = (fm.findFragmentById(R.id.flContent) as BaseFragment).getLevel()
                if (df.getLevel() <= level) {
                    isPopBackStack = true
                }
            } catch (e: Exception) {
            }
        }
    }


    /**
     * Constructor
     */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is BaseActivity) {
            mBaseActivity = context as BaseActivity
            mBaseActivity?.onFragmentAttached()
        }
    }

    override fun onDetach() {
        mBaseActivity = null
        super.onDetach()
    }

    /*Disable animation*/
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (isDisableAnimation) {
            val animation = object : Animation() {}
            animation.duration = 0
            return animation
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    /**
     * Top methods
     */

    /*Fragment back*/
    fun pop() {
        if (activity == null) return
        popBackStack(activity!!.supportFragmentManager, this)
        isPopBackStack = false
    }

    fun popTo(cls: Class<*> = MainFragment::class.java) {
        if (activity == null) return
        val fm = activity!!.supportFragmentManager
        var fCurrent = fm.findFragmentById(R.id.flContent)
        while (!cls.isInstance(fCurrent)) {
            popBackStack(fm, fCurrent as BaseFragment)
            isPopBackStack = false
            fCurrent = fm.findFragmentById(R.id.flContent)
        }
    }

    /*Fragment child*/
    fun setFragmentChild(fragment: BaseFragment, containerViewId: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
            .addToBackStack(fragment.getName())
            .commit()
    }

    fun clearBackStackChild() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    /*Level*/
    fun getLevel(): Int {
        return mLevel
    }

    fun setLevel(level: Int) {
        mLevel = level
    }

    /*Context*/
      fun getContext1(): Context? {
        return mContext
    }

    /*Name*/
    private fun getName(): String {
        return "Level$mLevel"
    }

    /*Network*/
    fun isNetwork(): Boolean? {
        return mBaseActivity?.isNetworkConnected
    }

    /*Loading*/
    fun showLoading() {
        mBaseActivity?.showLoading()
    }

    fun hideLoading() {
        mBaseActivity?.hideLoading()
    }

    /*Keyboard*/
    fun showKeyboard() {
        mBaseActivity?.showKeyboard()
    }

    fun hideKeyboard() {
        mBaseActivity?.hideKeyboard()
    }

    /*KeyBack*/
    fun onBackPressed() {
        hideLoading()
        hideKeyboard()
    }

    /*PerformClick*/
    fun loginSuccess() {
        Handler().postDelayed({
            if ((activity is MainActivity) && (activity as MainActivity).isAlive) {
                if (performClick != null) {
                    performClick?.performClick()
                }
            }
        }, TRANSITION_DELAY)
    }

    /**
     * Class
     */
    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}