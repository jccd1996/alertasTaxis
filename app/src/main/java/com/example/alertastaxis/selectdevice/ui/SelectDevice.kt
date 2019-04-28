package com.example.alertastaxis.selectdevice.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alertastaxis.R
import com.example.alertastaxis.selectdevice.ISelectDeviceMVP
import com.example.alertastaxis.selectdevice.SelectDevicePresenter
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDevice : AppCompatActivity(), ISelectDeviceMVP.view {

    private lateinit var presenter: ISelectDeviceMVP.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)
        presenter = SelectDevicePresenter(this)

    }

    override fun onResume() {
        super.onResume()
        presenter.verifiyState()
        presenter.paredDevice()

    }

    override fun getRecyclerView() = rvSelectDevice
    override fun getActivity() = this

}
