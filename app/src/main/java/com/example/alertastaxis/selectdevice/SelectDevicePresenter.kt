package com.example.alertastaxis.selectdevice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alertastaxis.R
import com.example.alertastaxis.selectdevice.ui.AdapterSelectDevice
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class SelectDevicePresenter(private var view: ISelectDeviceMVP.view) : ISelectDeviceMVP.presenter {

    private var mBtAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private lateinit var mPairedDevices: Set<BluetoothDevice>
    val adapter = GroupAdapter<ViewHolder>()

    override fun verifiyState() {
        if (!mBtAdapter.isEnabled) {
            Toast.makeText(view.getActivity(), R.string.active_bluetooth, Toast.LENGTH_SHORT).show()
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(view.getActivity(), enableBtIntent, 1, null)
        }
    }

    override fun paredDevice() {
        view.getRecyclerView().apply {
            layoutManager = LinearLayoutManager(view.getActivity())
        }
        mPairedDevices = mBtAdapter.bondedDevices
        if (mPairedDevices.isNotEmpty()) {
            for (device in mPairedDevices) {
                val activeDevice = Device(device.name, device.address)
                adapter.add(AdapterSelectDevice(activeDevice))
            }
        }
        view.getRecyclerView().adapter = adapter
    }
}