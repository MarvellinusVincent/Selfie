package com.example.selfieaday

/**
 * Abstract class representing a measurable sensor.
 *
 * @param sensorType The type of the sensor.
 */
abstract class MeasurableSensor(
    protected val sensorType: Int
) {

    /**
     * Callback for handling changes in sensor values.
     */
    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    /**
     * Indicates whether the sensor exists on the device.
     */
    abstract val doesSensorExist: Boolean

    /**
     * Start listening for sensor events.
     */
    abstract fun startListening()

    /**
     * Stop listening for sensor events.
     */
    abstract fun stopListening()

    /**
     * Set a listener to handle changes in sensor values.
     *
     * @param listener The listener to be notified when sensor values change.
     */
    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }
}
