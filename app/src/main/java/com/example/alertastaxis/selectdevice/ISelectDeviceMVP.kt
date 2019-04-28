package com.example.alertastaxis.selectdevice

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import androidx.recyclerview.widget.RecyclerView

interface ISelectDeviceMVP {

    interface view{
        fun getRecyclerView(): RecyclerView
        fun getActivity(): Activity
    }
    interface presenter{
        fun paredDevice()
        fun verifiyState()
    }

}