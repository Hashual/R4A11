package fr.unilim.iut.shi_fou_mi

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import fr.unilim.iut.shi_fou_mi.connectivity.BluetoothGameManager
import fr.unilim.iut.shi_fou_mi.ui.AppNavigation
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import java.io.IOException
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        println("un truc on activtyresult")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetooth = BluetoothGameManager(this.baseContext, this)
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
