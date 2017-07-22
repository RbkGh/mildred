package com.swiftpot.android.mildred


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.swiftpot.android.mildred.fragments.FragmentHome

class MainActivity : AppCompatActivity(), FragmentHome.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tx:FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentHome:FragmentHome
        //fragmentHome.
        tx.add(R.id.homeLayout, FragmentHome.newInstance("",""))
        tx.commit()
    }


}
