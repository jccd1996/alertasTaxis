package com.example.alertastaxis.main.ui

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.alertastaxis.R
import com.example.alertastaxis.db.prefs.SessionManager
import com.example.alertastaxis.main.IMainMVP
import java.io.IOException
import java.util.*

class ConnectDevice(
    val context: Context, var isConnect: Boolean, private var btSocket: BluetoothSocket?,
    var btAdapter: BluetoothAdapter?, var address: String, var bluetoothIn: Handler,
    var handlerState: Int,var viewHidden:IMainMVP.view
) : AsyncTask<Void, Void, String>() {

    private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var connectSuccess = true
    lateinit var m_progress: ProgressDialog
    private var MyConexionBT: ConnectedThread? = null


    override fun onPreExecute() {
        super.onPreExecute()
        if (SessionManager(context).getDeviceId() != "") {
            btSocket?.close()
        }
        m_progress = ProgressDialog.show(context, context.getString(R.string.connected),
            context.getString(R.string.wait_moment))
    }

    override fun doInBackground(vararg params: Void?): String {
        try {
            if (!isConnect) {
                btAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = btAdapter!!.getRemoteDevice(address)
                btSocket = device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                btSocket!!.connect()
                MyConexionBT = ConnectedThread(btSocket!!, bluetoothIn, handlerState)
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
            Toast.makeText(context, R.string.cant_connect, Toast.LENGTH_SHORT).show()
            viewHidden.hiddenItems()
        } else {
            isConnect = true
            Toast.makeText(context, R.string.succesfull_connect, Toast.LENGTH_SHORT).show()
        }
        m_progress.dismiss()
    }

    fun closeSession() {
        btSocket?.close()
    }
}

