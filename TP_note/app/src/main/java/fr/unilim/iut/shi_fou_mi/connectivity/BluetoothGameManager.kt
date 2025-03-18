package fr.unilim.iut.shi_fou_mi.connectivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat


class BluetoothGameManager (context: Context, activity : Activity) {
    val REQUEST_ENABLE_BT = 1

    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    init {
        if (bluetoothManager == null) {
            // Device doesn't support Bluetooth
        }
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        println(bluetoothAdapter?.isEnabled)
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        else{

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.BLUETOOTH_CONNECT)) {
                } else {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT) , 0);
                }
            } else {
                // Permission has already been granted
            }

        }

        println("BluetoothGameManager created")

    }


}