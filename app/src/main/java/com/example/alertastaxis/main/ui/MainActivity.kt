package com.example.alertastaxis.main.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.alertastaxis.R
import com.example.alertastaxis.db.prefs.SessionManager
import com.example.alertastaxis.main.IMainMVP
import com.example.alertastaxis.main.MainPresenter
import com.example.alertastaxis.selectdevice.ui.SelectDevice
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),IMainMVP.view {

    private lateinit var presenter: IMainMVP.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        presenter.validateSelectDevice()

        btReturnSelectDevice.setOnClickListener {
            presenter.clearData()
            goToSelectDeviceActivity()
        }



        Log.d("STATE_HERE",SessionManager(this).getDeviceId())
        presenter.connectDevicePresenter()
        presenter.readData()
    }

    override fun getContext(): Context = this

    override fun setState(string: String) {
        if (string.equals("1")){
            btAlert.text = "ALERTA"
            btAlert.setBackgroundColor(Color.RED)
        }else{
            btAlert.text = "DESACTIVADO"
            btAlert.setBackgroundColor(Color.GRAY)
        }

    }

    override fun goToSelectDeviceActivity() {
        val intent = Intent(this, SelectDevice::class.java)
        ContextCompat.startActivity(this, intent, null)
        finish()
    }

    override fun onRestart() {
        super.onRestart()

    }
}
