package fr.unilim.iut.shi_fou_mi.connectivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.ui.util.packInts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runInterruptible


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.S)
class BluetoothGameManager (context: Context, activity : Activity) {
    val REQUEST_ENABLE_BT = 1

    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private var devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())

    fun getBluetoothDevices(): StateFlow<List<BluetoothDevice>> {
        bluetoothAdapter?.startDiscovery()
        devices.value = emptyList()
        bluetoothAdapter?.bondedDevices?.forEach {
            devices.value = devices.value + it
        }
        return devices.asStateFlow()
    }

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
        bluetoothAdapter?.isEnabled
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        else{
            checkMultiplePermissions(activity, arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )){
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


    }
    public val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null && device.name != null) {
                        if (!devices.value.contains(device)) {
                            devices.value = devices.value + device
                            println("Device found: ${device.name}")
                        }
                    }
                }
            }
        }
    }




}