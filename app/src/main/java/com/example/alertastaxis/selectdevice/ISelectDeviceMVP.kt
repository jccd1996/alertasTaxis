package com.example.alertastaxis.selectdevice

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView

interface ISelectDeviceMVP {

    interface view{
        fun getRecyclerView(): RecyclerView
        fun getActivity(): Activity
        fun goToMainActivity()
    }
    interface presenter{
        fun paredDevice()
        fun verifiyState()
    }

}