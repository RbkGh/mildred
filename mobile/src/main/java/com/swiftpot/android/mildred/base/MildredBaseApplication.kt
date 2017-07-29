package com.swiftpot.android.mildred.base

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.swiftpot.android.mildred.BuildConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * Created by ace on 22/07/2017.
 */
class MildredBaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install()
        //init hawk db
        Hawk.init(baseContext).build()
    }
}