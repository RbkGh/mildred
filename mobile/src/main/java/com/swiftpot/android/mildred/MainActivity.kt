package com.swiftpot.android.mildred


import android.net.Uri
import android.os.Bundle
import com.swiftpot.android.mildred.fragments.FragmentHome

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity

class MainActivity : SwipeBackActivity(), FragmentHome.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (findFragment(FragmentHome::class.java) == null) {
            loadRootFragment(R.id.homeLayout, FragmentHome.newInstance("", ""))
        }
    }


}
