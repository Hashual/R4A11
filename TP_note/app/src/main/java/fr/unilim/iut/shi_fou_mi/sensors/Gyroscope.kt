package fr.unilim.iut.shi_fou_mi.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Cette classe gère la détection des mouvements via le gyroscope.
 *
 * @param context Le contexte de l'application.
 * @param onThreeDownwardMovements Callback appelée lorsque 3 mouvements vers le bas sont détectés.
 */
class Gyroscope(
    context: Context,
    private val onThreeDownwardMovements: () -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val NS2S = 1.0f / 1_000_000_000.0f
    private val deltaRotationVector = FloatArray(4) { 0f }
    private var timestamp: Long = 0L

    private val EPSILON = 0.0000001f

    private val downwardThreshold = 1.0f

    private var downwardMovementCount = 0
    private var lastMovementTimestamp: Long = 0L
    private val movementCooldown = 1_000_000_000L

    fun start() {
        gyroscopeSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        if (timestamp != 0L) {
            val dT = (event.timestamp - timestamp) * NS2S

            var axisX = event.values[0]
            var axisY = event.values[1]
            var axisZ = event.values[2]

            val omegaMagnitude = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude
                axisY /= omegaMagnitude
                axisZ /= omegaMagnitude
            }

            val thetaOverTwo = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo = sin(thetaOverTwo)
            val cosThetaOverTwo = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * axisX
            deltaRotationVector[1] = sinThetaOverTwo * axisY
            deltaRotationVector[2] = sinThetaOverTwo * axisZ
            deltaRotationVector[3] = cosThetaOverTwo

        }
        timestamp = event.timestamp

        if (event.values[0] > downwardThreshold &&
            (event.timestamp - lastMovementTimestamp) > movementCooldown
        ) {
            downwardMovementCount++
            lastMovementTimestamp = event.timestamp

            if (downwardMovementCount >= 3) {
                downwardMovementCount = 0
                onThreeDownwardMovements()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non utilisé ici.
    }
}
