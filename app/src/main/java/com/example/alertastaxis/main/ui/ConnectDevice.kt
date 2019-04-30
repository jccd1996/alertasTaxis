package com.example.alertastaxis.main.ui

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.util.*

class ConnectDevice(val context: Context, var isConnect: Boolean, private var btSocket: BluetoothSocket?,
                    var btAdapter: BluetoothAdapter?, var address:String, var bluetoothIn:Handler,
                    var handlerState: Int): AsyncTask<Void, Void, String>(){

    private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var connectSuccess = true
    lateinit var m_progress: ProgressDialog
    private var MyConexionBT: ConnectedThread? = null


    override fun onPreExecute() {
        super.onPreExecute()
        m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
    }

    override fun doInBackground(vararg params: Void?): String {
        try {
            if (!isConnect) {
                btAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = btAdapter!!.getRemoteDevice(address)
                btSocket = device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                btSocket!!.connect()
                MyConexionBT = ConnectedThread(btSocket!!,bluetoothIn,handlerState)
                MyConexionBT?.start()
            }
        } catch (e: IOException) {
            connectSuccess = false
            e.printStackTrace()
        }
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (!connectSuccess) {
            Log.i("data", "couldn't connect")
        } else {
            isConnect = true
            Log.d("CONECTADO : ", address)
        }
        m_progress.dismiss()
    }
}

