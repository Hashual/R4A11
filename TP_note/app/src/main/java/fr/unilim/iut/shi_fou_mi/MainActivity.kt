package fr.unilim.iut.shi_fou_mi

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import fr.unilim.iut.shi_fou_mi.connectivity.BluetoothGameManager
import fr.unilim.iut.shi_fou_mi.ui.AppNavigation
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import java.io.IOException
import java.util.UUID

class MainActivity : ComponentActivity() {
    var bluetoothAdapter: BluetoothAdapter? = null

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        println("un truc on activtyresult")
    }

    @SuppressLint("MissingPermission")
    private inner class AcceptThread : Thread() {

        private val MY_UUID = UUID.randomUUID()
        private val NAME = "ShiFouMi"
        private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID)
        }

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
                    Log.e(TAG, "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
                socket?.also {
                    //manageMyConnectedSocket(it)
                    mmServerSocket?.close()
                    shouldLoop = false
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        fun cancel() {
            try {
                mmServerSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetooth = BluetoothGameManager(this.baseContext, this)
        bluetoothAdapter = bluetooth.bluetoothAdapter

        enableEdgeToEdge()
        setContent {
            Shi_fou_miTheme {
                AppNavigation()
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shi_fou_miTheme {
        AppNavigation()
    }
}
