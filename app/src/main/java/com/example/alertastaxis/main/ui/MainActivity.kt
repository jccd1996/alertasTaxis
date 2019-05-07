package com.example.alertastaxis.main.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.alertastaxis.R
import com.example.alertastaxis.main.IMainMVP
import com.example.alertastaxis.main.MainPresenter
import com.example.alertastaxis.selectdevice.ui.SelectDevice
import kotlinx.android.synthetic.main.activity_main.*



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
        presenter.connectDevicePresenter()
    }

    override fun getContext(): Context = this

    override fun setState(string: String) {
        if (string.equals("1")){
            btAlert.text = this.getString(R.string.alert).toUpperCase()
            btAlert.setBackgroundColor(Color.RED)
        }else{
            btAlert.text = this.getString(R.string.disabled)
            btAlert.setBackgroundColor(Color.GRAY)
        }
    }

    override fun goToSelectDeviceActivity() {
        val intent = Intent(this, SelectDevice::class.java)
        ContextCompat.startActivity(this, intent, null)
        finish()
    }

    override fun hiddenItems() {
        etNameToSend.visibility = View.GONE
        btAlert.visibility = View.GONE
        btFinishAlert.visibility = View.GONE
        ivNoConnection.visibility = View.VISIBLE
        tvNoConnection.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
