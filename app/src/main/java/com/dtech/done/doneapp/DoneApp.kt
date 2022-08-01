package com.dtech.done.doneapp

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.dtech.done.doneapp.data.remote.ApiClient
import com.dtech.done.doneapp.data.remote.ApiInterface
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.helper.TinyDB
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class DoneApp : Application() {
    private lateinit var appSharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private var SHARED_NAME = "com.dtech.Do_ne.app"


    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initPreferences()
        FirebaseApp.initializeApp(instance!!)
        configRetrofit()
        Places.initialize(applicationContext, context.resources.getString(R.string.google_key))
        val placesClient = Places.createClient(this)
         getFcmTokken()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            context.InternetSpeedChecker()
    }

    private fun initPreferences() {
        this.appSharedPrefs = getSharedPreferences(SHARED_NAME, Activity.MODE_PRIVATE)
        this.prefsEditor = appSharedPrefs.edit()
        db = TinyDB(applicationContext)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Log.i(TAG, "Freeing memory ...")

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.v(TAG, "public void onTrimMemory (int level)")
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    companion object {
        lateinit var db: TinyDB

        @SuppressLint("StaticFieldLeak")
        private var instance: DoneApp? = null

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        private val TAG = DoneApp::class.java.name
        lateinit var apiService: ApiInterface

        private var parentJob = Job()
        private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
        val scope = CoroutineScope(coroutineContext)


        fun configRetrofit() {
            apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        }


    }

    private fun getFcmTokken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val tokken = task.result
            db.putString(Constants.DEVICE_ID, tokken!!)
            Log.d("Token", "New Token : $tokken")
        })

    }
}