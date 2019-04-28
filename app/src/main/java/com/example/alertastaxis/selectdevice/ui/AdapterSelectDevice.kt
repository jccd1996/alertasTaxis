package com.example.alertastaxis.selectdevice.ui

import com.example.alertastaxis.R
import com.example.alertastaxis.selectdevice.Device
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_device.view.*

class AdapterSelectDevice(val device: Device): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.item_device
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvNameDevice.text = device.name
        viewHolder.itemView.tvDeviceId.text = device.id
    }
}