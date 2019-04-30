package com.example.alertastaxis.main

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import com.example.alertastaxis.db.prefs.SessionManager
import com.example.alertastaxis.main.ui.ConnectDevice
import com.example.alertastaxis.main.ui.ConnectedThread
import kotlinx.android.synthetic.main.activity_main.*

class MainPresenter(private var view: IMainMVP.view) : IMainMVP.presenter {


    var sessionManager: SessionManager = SessionManager(view.getContext())
    private val btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private var isConnected = false
    private var doLogOut = false
    private lateinit var bluetoothIn: Handler
    val handlerState = 0
    private val DataStringIN = StringBuilder()

    override fun validateSelectDevice() {
        val deviceId = sessionManager.getDeviceId()
        if (deviceId == "") {
            doLogOut = true
            sessionManager.returnSelectDevice()
            view.goToSelectDeviceActivity()
        }
    }

    override fun clearData() {
        sessionManager.returnSelectDevice()
    }
    override fun connectDevicePresenter() {
        if (!doLogOut){
            readData()

            ConnectDevice(view.getContext(),isConnected,btSocket,btAdapter,sessionManager.getDeviceId(),bluetoothIn,
                handlerState).execute()
        }
    }

    override fun readData(){
        bluetoothIn = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: android.os.Message) {
                if (msg.what == handlerState) {
                    val readMessage = msg.obj as String
                    DataStringIN.append(readMessage)

                    val endOfLineIndex = DataStringIN.indexOf("#")
                    if (endOfLineIndex > 0) {
                        val dataInPrint = DataStringIN.substring(0, endOfLineIndex)
                        Log.d("RECIBIENDO: ",dataInPrint)
                        view.setState(dataInPrint)
                        DataStringIN.delete(0, DataStringIN.length)
                    }
                }
            }
        }
    }
}
