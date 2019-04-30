package com.example.alertastaxis.selectdevice.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.alertastaxis.R
import com.example.alertastaxis.main.ui.MainActivity
import com.example.alertastaxis.selectdevice.ISelectDeviceMVP
import com.example.alertastaxis.selectdevice.SelectDevicePresenter
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDevice : AppCompatActivity(), ISelectDeviceMVP.view {

    private lateinit var presenter: ISelectDeviceMVP.presenter
    companion object{
        val EXTRA_ADDRESS: String = "Device_address"
    }
    val REQUEST_STORAGE = 1
    val REQUEST_PHONE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)
        presenter = SelectDevicePresenter(this)
        presenter.verifiyState()
        checkSternalStoragePermission()

    }
    override fun onResume() {
        super.onResume()
        presenter.paredDevice()
    }

    override fun getRecyclerView() = rvSelectDevice
    override fun getActivity() = this
    override fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkSternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            if (result == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_STORAGE
                )
            } else if (result == PackageManager.PERMISSION_GRANTED) {
                val resultPhone = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (resultPhone == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_PHONE
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                checkSternalStoragePermission()
            }
        }
    }
}
