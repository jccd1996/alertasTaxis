package com.example.alertastaxis.main.ui

import android.bluetooth.BluetoothSocket
import android.os.Handler
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread(socket: BluetoothSocket, var bluetoothIn: Handler, var handlerState: Int) : Thread() {
    private val mmInStream: InputStream?
    private val mmOutStream: OutputStream?
    init {
        var tmpIn: InputStream? = null
        var tmpOut: OutputStream? = null
        try {
            tmpIn = socket.inputStream
            tmpOut = socket.outputStream
        } catch (e: IOException) {
        }

        mmInStream = tmpIn
        mmOutStream = tmpOut
    }

    override fun run() {
        val buffer = ByteArray(256)
        var bytes: Int
        while (true) {
            try {
                bytes = mmInStream!!.read(buffer)
                val readMessage = String(buffer, 0, bytes)
                bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget()
            } catch (e: IOException) {
                break
            }

        }
    }
}