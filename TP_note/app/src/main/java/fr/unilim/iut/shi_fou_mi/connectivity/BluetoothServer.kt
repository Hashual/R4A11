package fr.unilim.iut.shi_fou_mi.connectivity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothServer(bluetoothAdapter: BluetoothAdapter, val playerName: String) : Thread(){

    private val MY_UUID = UUID.fromString("cd398e30-03d6-11f0-9417-bc24113b978d")
    private val NAME = "ShiFouMi"
    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID)
    }
    var transfert: BluetoothTransfert.ConnectedThread? = null


    override fun run() {
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                mmServerSocket?.accept()
            } catch (e: IOException) {
                println("Socket's accept() method failed")
                shouldLoop = false
                null
            }
            socket?.also {
                //manageMyConnectedSocket(it)
                mmServerSocket?.close()
                shouldLoop = false
            }

            transfert =
                BluetoothTransfert(Handler(Looper.getMainLooper(), object : Handler.Callback {
                    override fun handleMessage(msg: android.os.Message): Boolean {
                        val received = String(msg.obj as ByteArray, Charsets.UTF_8)
                        println("Received: $received")
                        return true
                    }
                })).ConnectedThread(socket!!)
            transfert!!.start()
            sendMessage("name:$playerName")

        }
    }
    fun sendMessage (message: String) {
        transfert?.writeString(message)
    }

    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
            println("Could not close the connect socket")
        }
    }
    init {
        println("server started")
    }
}
