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
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.S)
class BluetoothGameManager (context: Context, activity : Activity) {
    val REQUEST_ENABLE_BT = 1

    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    fun checkMultiplePermissions(
        activity: Activity,
        permissions: Array<String>,
        onPermissionsGranted: () -> Unit
    ) {
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isEmpty()) {
            // Toutes les permissions sont déjà accordées
            onPermissionsGranted()
        } else {
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), 0)
        }
    }


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
            checkMultiplePermissions(activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)) {
                val server = BluetoothServer(bluetoothAdapter!!)
                server.start()
                activity.window.decorView.postDelayed({
                    val bluetoothDevice = bluetoothAdapter.bondedDevices.find { it.name == "hello world" }
                    if (bluetoothDevice != null) {
                        val client = BluetoothClient(bluetoothAdapter, bluetoothDevice)
                        client.start()
                    }
                }, 3000)
            }
        }

        println("BluetoothGameManager created")

    }




}