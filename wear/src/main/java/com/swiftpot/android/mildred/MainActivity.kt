package com.swiftpot.android.mildred

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.view.BoxInsetLayout
import android.view.View
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : WearableActivity() {

    private var mContainerView: BoxInsetLayout? = null
    private var mTextView: TextView? = null
    private var mClockView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmbientEnabled()

        mContainerView = findViewById<View>(R.id.container) as BoxInsetLayout
        mTextView = findViewById<View>(R.id.text) as TextView
        mClockView = findViewById<View>(R.id.clock) as TextView
    }

    override fun onEnterAmbient(ambientDetails: Bundle?) {
        super.onEnterAmbient(ambientDetails)
        updateDisplay()
    }

    override fun onUpdateAmbient() {
        super.onUpdateAmbient()
        updateDisplay()
    }

    override fun onExitAmbient() {
        updateDisplay()
        super.onExitAmbient()
    }

    private fun updateDisplay() {
        if (isAmbient) {
            mContainerView!!.setBackgroundColor(resources.getColor(android.R.color.black))
            mTextView!!.setTextColor(resources.getColor(android.R.color.white))
            mClockView!!.visibility = View.VISIBLE

            mClockView!!.text = AMBIENT_DATE_FORMAT.format(Date())
        } else {
            mContainerView!!.background = null
            mTextView!!.setTextColor(resources.getColor(android.R.color.black))
            mClockView!!.visibility = View.GONE
        }
    }

    companion object {

        private val AMBIENT_DATE_FORMAT = SimpleDateFormat("HH:mm", Locale.US)
    }
}
