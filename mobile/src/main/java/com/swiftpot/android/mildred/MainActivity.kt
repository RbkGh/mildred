package com.swiftpot.android.mildred


import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.sheets.v4.SheetsScopes
import com.orhanobut.hawk.Hawk
import com.swiftpot.android.mildred.base.MildredBaseActivity
import com.swiftpot.android.mildred.fragments.FragmentHome
import com.swiftpot.android.mildred.util.AppConstants


class MainActivity : MildredBaseActivity(), FragmentHome.OnFragmentInteractionListener {

    var mCredential: GoogleAccountCredential? = null

    private val SCOPES = mutableListOf(SheetsScopes.SPREADSHEETS_READONLY, SheetsScopes.SPREADSHEETS)

    val REQUEST_ACCOUNT_PICKER = 1000

    val REQUEST_AUTHORIZATION = 1001

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize hawk
        Hawk.init(this).build()

        mCredential = GoogleAccountCredential.usingOAuth2(applicationContext,
                SCOPES)
                .setBackOff(ExponentialBackOff())
        if (!this.isGoogleSheetsAccountAccessGranted())
            this.grantGoogleSheetsAccountAccess()

        if (findFragment(FragmentHome::class.java) == null) {
            loadRootFragment(R.id.homeLayout, FragmentHome.newInstance("", ""))
        }
    }

    fun isGoogleSheetsAccountAccessGranted(): Boolean {
        val isGoogleSheetsAccountAccessGranted: Boolean = Hawk.get(AppConstants.GOOGLE_SHEETS_ACCESS_STATE_KEY, false)
        return isGoogleSheetsAccountAccessGranted
    }

    fun grantGoogleSheetsAccountAccess() {
        if (this.isGooglePlayServicesAvailable()) {
            val accountName: String = Hawk.get(AppConstants.GOOGLE_USER_ACCOUNT_NAME_KEY, "")
            if (!accountName.isEmpty()) {
                mCredential!!.setSelectedAccountName(accountName)
                this.chooseGoogleAccount()
            } else {

            }
        }
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     * *     date on this device; false otherwise.
     */
    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    fun chooseGoogleAccount() {
        // Start a dialog from which the user can choose an account
        startActivityForResult(
                mCredential!!.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER)
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private fun isDeviceOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.getActiveNetworkInfo()
        return networkInfo != null && networkInfo!!.isConnected()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ACCOUNT_PICKER -> accountPickerHandler(resultCode, data)
            REQUEST_AUTHORIZATION -> requestAuthHandler(resultCode)
        }

    }

    fun accountPickerHandler(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null && data.extras != null) {
            val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            if (accountName != null) {
                Hawk.put(AppConstants.GOOGLE_USER_ACCOUNT_NAME_KEY, accountName)
                mCredential!!.setSelectedAccountName(accountName)
                initiateGoogleSheetsApiCall(mCredential!!)
            }
        }
    }

    fun requestAuthHandler(resultCode: Int) {
        if (resultCode == Activity.RESULT_OK) {
            initiateGoogleSheetsApiCall(mCredential!!)
        }
    }

    fun initiateGoogleSheetsApiCall(mCredential: GoogleAccountCredential) {

    }
}
