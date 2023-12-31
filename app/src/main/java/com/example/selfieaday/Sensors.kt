package com.example.selfieaday

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

/**
 * Sensor class for the accelerometer.
 *
 * @param context The application context.
 */
class AccelerometerSensor(
    context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER
)
