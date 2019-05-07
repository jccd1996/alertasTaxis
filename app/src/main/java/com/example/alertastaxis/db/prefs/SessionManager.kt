package com.example.alertastaxis.db.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.alertastaxis.db.model.Device

class SessionManager(private var context: Context, private val name: String? = null) {

    private val DEVICE_NAME = "device_name"
    private val DEVICE_ID = "device_email"

    private val sharedPreferencesUser: SharedPreferences by lazy {
        context.getSharedPreferences(name ?: javaClass.simpleName, Context.MODE_PRIVATE)
    }

    fun saveDevice(device: Device) {
        val editor = sharedPreferencesUser.edit()
        editor.putString(DEVICE_NAME, device.name)
        editor.putString(DEVICE_ID, device.id)
        editor.apply()
    }

    fun getDeviceId() = sharedPreferencesUser.getString(DEVICE_ID, "")

    fun returnSelectDevice() {
        val editor = sharedPreferencesUser.edit()
        editor.clear()
        editor.apply()
    }
}