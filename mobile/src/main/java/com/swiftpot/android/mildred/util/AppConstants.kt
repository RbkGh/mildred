package com.swiftpot.android.mildred.util

/**
 * AppConstants for Geofencing
 */

internal object AppConstants {

    private val PACKAGE_NAME = "com.swiftpot.android.mildred"

    val GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY"

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private val GEOFENCE_EXPIRATION_IN_HOURS: Long = 720

    /**
     * For this sample, geofences expire after twelve hours.
     */
    val GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000
    val GEOFENCE_RADIUS_IN_METERS: Float = 100f // 1 mile, 1.6 km

    val GOOGLE_SHEETS_ACCESS_STATE_KEY: String = "SHEETS_ACCESS_KEY"

    val GOOGLE_USER_ACCOUNT_NAME_KEY: String = "GOOGLE_USER_ACCOUNT_NAME_KEY"


}
