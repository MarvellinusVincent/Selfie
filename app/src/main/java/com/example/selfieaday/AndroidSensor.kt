package com.example.selfieaday

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Abstract class representing an Android sensor.
 *
 * @param context The context used to access the system service.
 * @param sensorFeature The feature string associated with the sensor.
 * @param sensorType The type of the sensor.
 */
abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    sensorType: Int
) : MeasurableSensor(sensorType), SensorEventListener {

    /**
     * Check if the sensor exists on the device.
     */
    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    /**
     * Start listening for sensor events.
     */
    override fun startListening() {
        if (!doesSensorExist) {
            return
        }
        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    /**
     * Stop listening for sensor events.
     */
    override fun stopListening() {
        if (!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
    }

    /**
     * Callback method called when the sensor values change.
     *
     * @param event The [SensorEvent] containing the sensor values.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    /**
     * Callback method called when the accuracy of the sensor changes.
     *
     * @param sensor The [Sensor] whose accuracy changed.
     * @param accuracy The new accuracy value.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}
