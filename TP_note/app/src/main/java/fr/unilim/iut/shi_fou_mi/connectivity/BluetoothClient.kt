package fr.unilim.iut.shi_fou_mi.connectivity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothClient(val bluetoothAdapter: BluetoothAdapter, val device: BluetoothDevice) : Thread() {
        val MY_UUID = UUID.fromString("cd398e30-03d6-11f0-9417-bc24113b978d")

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)
        }

        override fun run() {
            bluetoothAdapter.cancelDiscovery()

            mmSocket?.let { socket ->
                socket.connect()
                println("Connected to server")
                val transfert = BluetoothTransfert(Handler(Looper.getMainLooper())).ConnectedThread(socket)
                transfert.start()
                transfert.writeString("hello")
//                manageMyConnectedSocket(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                println("Could not close the client socket")
            }
        }
        init {
            println("Connecting to " + device.name)
        }
    }
