package com.example.emiloan.base

import android.content.Context
import android.content.SharedPreferences
import com.example.emiloan.base.Constant.KEY_HAS_FEEDBACK
import com.example.emiloan.base.Constant.PREF_NAME

class AppPreferenceManager private constructor(context: Context) {

    companion object {

         @Volatile
        private var instance: AppPreferenceManager? = null

        fun getInstance(context: Context): AppPreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: AppPreferenceManager(context.applicationContext).also { instance = it }
            }
        }
    }
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setHasFeedback(hasFeedback: Boolean) {
        prefs.edit().putBoolean(KEY_HAS_FEEDBACK, hasFeedback).apply()
    }

    fun hasFeedback(): Boolean = prefs.getBoolean(KEY_HAS_FEEDBACK, false)

}