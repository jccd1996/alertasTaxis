package com.example.alertastaxis.main

import android.content.Context

interface IMainMVP {

    interface view{
        fun getContext():Context
        fun goToSelectDeviceActivity()
        fun setState(string: String)
        fun hiddenItems()
    }

    interface presenter{
        fun validateSelectDevice()
        fun clearData()
        fun connectDevicePresenter()
        fun readData()
    }
}