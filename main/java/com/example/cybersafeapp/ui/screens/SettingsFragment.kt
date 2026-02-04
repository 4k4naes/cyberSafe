package com.example.cybersafeapp.ui.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    companion object {
        private const val PREFS_NAME = "app_settings"
        private const val DARK_MODE_KEY = "dark_mode"

        fun isDarkMode(context: Context): Boolean {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getBoolean(DARK_MODE_KEY, true)
        }

        fun setDarkMode(context: Context, enabled: Boolean) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(DARK_MODE_KEY, enabled).apply()
        }
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<Switch>(R.id.switch_theme)

        switchTheme.isChecked = isDarkMode(requireContext())

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(requireContext(), isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
