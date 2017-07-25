package com.swiftpot.android.mildred.services

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.NotificationCompat
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.LocationServices
import com.swiftpot.android.mildred.MainActivity
import com.swiftpot.android.mildred.R
import com.swiftpot.android.mildred.util.GeofenceErrorMessages

/**
 * Created by ace on 24/07/2017.
 */
open class GeofenceTransitionsIntentService : IntentService("") {
    val TAG: String = "GeofenceTransitionsIS"
    var mGeofencingClient: GeofencingClient? = null

    override fun onHandleIntent(intent: Intent?) {
        mGeofencingClient = LocationServices.getGeofencingClient(this)

        val geofencingEvent: GeofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Log.e(TAG, GeofenceErrorMessages().getErrorString(this, geofencingEvent.errorCode))
            return
        }
        val geofenceTansition: Int = geofencingEvent.geofenceTransition
        if (geofenceTansition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTansition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            val triggeringGeofences: List<Geofence> = geofencingEvent.triggeringGeofences
            val geofenceTransitionDetails: String = getGeofenceTransitionDetails(geofenceTansition, triggeringGeofences)

            this.sendNotification(geofenceTransitionDetails)
            Log.i(TAG, geofenceTransitionDetails)
        } else {
            Log.e(TAG, "Geofence transition Error!!")
        }
    }

    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    private fun getGeofenceTransitionDetails(
            geofenceTransition: Int, triggeringGeofences: List<Geofence>): String {
        val geofenceTransitionString: String = getTransitionString(geofenceTransition)
        val triggeringGeofencesIdsList = arrayListOf<String>()
        for (geofence: Geofence in triggeringGeofences)
            triggeringGeofencesIdsList.add(geofence.requestId)
        val triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList)
        return geofenceTransitionString + ": " + triggeringGeofencesIdsString
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private fun getTransitionString(transitionType: Int): String {
        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER
            -> return "Arrived at WorkPlace"
            Geofence.GEOFENCE_TRANSITION_EXIT
            -> return "Exited WorkPlace"
            else
            -> return "Sorry,What did you just do? I'm confused!:("
        }
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private fun sendNotification(notificationDetails: String) {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val notificationPendingIntent: PendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory
                        .decodeResource(resources, R.mipmap.ic_launcher))
                .setColor(Color.BLUE)
                .setContentTitle(notificationDetails)
                .setContentText("Open App")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)

        val mNotificationNotificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //fire notification
        mNotificationNotificationManager.notify(0, builder.build())
    }
}