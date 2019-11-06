package com.example.permission_helper.helper

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import java.util.*

class SharedPreferencesHelper(context: Context) {
    private val editor: SharedPreferences.Editor
    private val settings: SharedPreferences

    companion object {
        const val PREF_FILE = "PREF_FILE"
    }

    init {
        settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        editor = settings.edit()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return settings.getBoolean(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return settings.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long): Long {
        return settings.getLong(key, defValue)
    }

    fun getFloat(key: String, defValue: Float): Float {
        return settings.getFloat(key, defValue)
    }

    fun getString(key: String, defValue: String): String? {
        return settings.getString(key, defValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun setLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun setFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.commit()
    }

    fun setString(key: String, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun setIntArray(key: String, list: ArrayList<Int>?) {
        var s = ""
        if (list == null) {
            return
        }

        for (i in list.indices) {
            s += list[i]
            if (i < list.size - 1) {
                s += ","
            }
        }
        setString(key, s)
    }

    fun getIntArray(key: String): ArrayList<Int> {
        val result = ArrayList<Int>()
        val s = getString(key, "")!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()


        for (i in s.indices) {
            if (!TextUtils.isEmpty(s[i])) {
                result.add(Integer.valueOf(s[i]))
            }
        }
        return result
    }

    fun contains(key: String): Boolean {
        return settings.contains(key)
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.commit()
    }

}
