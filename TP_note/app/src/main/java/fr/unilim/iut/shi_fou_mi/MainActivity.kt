package fr.unilim.iut.shi_fou_mi

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Half.EPSILON
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        enableEdgeToEdge()
        setContent {
            Shi_fou_miTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    onSensorChanged(sensorManager, sensor)
                }
            }
        }
    }
    // Create a constant to convert nanoseconds to seconds.
    private val NS2S = 1.0f / 1000000000.0f
    private val deltaRotationVector = FloatArray(4) { 0f }
    private var timestamp: Float = 0f

    fun onSensorChanged(event: SensorEvent?) {
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0f && event != null) {
            val dT = (event.timestamp - timestamp) * NS2S
            // Axis of the rotation sample, not normalized yet.
            var axisX: Float = event.values[0]
            var axisY: Float = event.values[1]
            var axisZ: Float = event.values[2]

            // Calculate the angular speed of the sample
            val omegaMagnitude: Float = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude
                axisY /= omegaMagnitude
                axisZ /= omegaMagnitude
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo: Float = sin(thetaOverTwo)
            val cosThetaOverTwo: Float = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * axisX
            deltaRotationVector[1] = sinThetaOverTwo * axisY
            deltaRotationVector[2] = sinThetaOverTwo * axisZ
            deltaRotationVector[3] = cosThetaOverTwo
        }
        timestamp = event?.timestamp?.toFloat() ?: 0f
        val deltaRotationMatrix = FloatArray(9) { 0f }
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shi_fou_miTheme {
        Greeting("Android")
    }
}