package base

import android.Manifest
import android.os.Bundle
import android.os.PersistableBundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity

/**
 * Created by ace on 23/07/2017.
 */
open class MildredBaseActivity : SwipeBackActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        //Less messier way of dealing with android M permissions
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
                .withErrorListener(object : PermissionRequestErrorListener {
                    override fun onError(p0: DexterError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
                .onSameThread()
                .check()
    }
}


