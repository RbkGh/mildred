package com.swiftpot.android.mildred.fragments

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.swiftpot.android.mildred.R
import com.swiftpot.android.mildred.services.GeofenceTransitionsIntentService
import me.yokeyword.fragmentation.SupportFragment
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentHome.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : SupportFragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    /**
     * Tracks whether the user requested to add or remove geofences, or to do neither.
     */
    private enum class PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    /**
     * Used when requesting to add or remove geofences.
     */
    private var mGeofencePendingIntent: PendingIntent? = null
    /**
     * Provides access to the Geofencing API.
     */
    private var mGeofencingClient: GeofencingClient? = null
    /**
     * The list of geofences used in this sample.
     */
    private var mGeofenceList: ArrayList<Geofence>? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var mPendingGeofenceTask = PendingGeofenceTask.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGeofenceList = arrayListOf()
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private fun getGeofencingRequest(): GeofencingRequest {
        val builder: GeofencingRequest.Builder = GeofencingRequest.Builder()
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofences(mGeofenceList)
        return builder.build()
    }

    private fun performPendingGeofenceTask() {
        if (mPendingGeofenceTask == PendingGeofenceTask.ADD) {
            this.addGeofence()
        }
    }

    private fun addGeofence() {

    }

    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        val permissionState: Int = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.

     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private fun getGeofencePendingIntent(): PendingIntent {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent as PendingIntent
        }
        val intent = Intent(activity, GeofenceTransitionsIntentService::class.java)
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): FragmentHome {
            val fragment = FragmentHome()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
