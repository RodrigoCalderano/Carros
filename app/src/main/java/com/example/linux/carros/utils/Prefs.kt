package com.example.linux.carros.utils

import android.content.SharedPreferences
import com.example.linux.carros.CarrosApplication

object Prefs {
    val PREF_ID = "carros"
    private fun prefs(): SharedPreferences{
        val context = CarrosApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }

    fun setInt(flag: String, valor: Int){
        val pref = prefs()
        val editor = pref.edit()
        editor.putInt(flag, valor)
        editor.apply()
    }

    fun getInt(flag: String): Int {
        val pref = prefs()
        val i = pref.getInt(flag, 0)
        return i
    }

    // Forma reduzida
    fun setString(flag: String, valor : String) = prefs().edit().putString(flag, valor).apply()


    fun getString(flag: String): String {
        val pref = prefs()
        val s = pref.getString(flag, "")
        return s
    }
}