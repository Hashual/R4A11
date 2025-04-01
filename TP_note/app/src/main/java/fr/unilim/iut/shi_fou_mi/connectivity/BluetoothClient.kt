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
class BluetoothClient(val bluetoothAdapter: BluetoothAdapter, val device: BluetoothDevice, var playerName : String, val receivedMessageCollback: (String) -> Unit ) : Thread() {
        val MY_UUID = UUID.fromString("cd398e30-03d6-11f0-9417-bc24113b978d")
        var transfert: BluetoothTransfert.ConnectedThread? = null


        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)
        }

        override fun run() {
            bluetoothAdapter.cancelDiscovery()

            mmSocket?.let { socket ->
                socket.connect()
                println("Connected to server")
                transfert = BluetoothTransfert(Handler(Looper.getMainLooper()) {
                    val received = String(it.obj as ByteArray, Charsets.UTF_8)
                    receivedMessageCollback(received)
                    return@Handler true
                }).ConnectedThread(socket)
                transfert!!.start()
                sendMessage("name:$playerName")
//                manageMyConnectedSocket(socket)
            }
        }
        fun sendMessage (message: String) {
            transfert?.writeString(message)
        }

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
