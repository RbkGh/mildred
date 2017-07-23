package base

import android.app.Application
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
    }
}