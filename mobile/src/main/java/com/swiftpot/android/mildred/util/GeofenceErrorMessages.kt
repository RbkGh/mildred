package com.swiftpot.android.mildred.util

import android.content.Context
import android.content.res.Resources
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.GeofenceStatusCodes

/**
 * Created by ace on 25/07/2017.
 */
class GeofenceErrorMessages {

    fun getErrorString(context: Context, e: Exception): String {
        if (e is ApiException) {
            return this.getErrorString(context, (e).statusCode)
        } else {
            return "Geofence Error Unavailable Now!"
        }
    }

    fun getErrorString(context: Context, errorCode: Int): String {
        val mResources: Resources = context.resources

        when (errorCode) {
            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE
            -> return "Geofence is not available now"
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES
            -> return "Too many geofences registered"
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS
            -> return "Too many pending intents available"
            else
            -> return "Uknown error"
        }
    }
}