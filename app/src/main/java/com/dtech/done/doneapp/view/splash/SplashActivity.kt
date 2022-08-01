package com.dtech.done.doneapp.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.view.auth.AuthActivity
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.languageselection.LanguageSelectionActivity
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.tour.TourActivity

class SplashActivity : ActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.myLooper()!!).postDelayed({
            if (DoneApp.db.getBoolean(Constants.CONST_IS_LOGIN , false)){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else {
                if (DoneApp.db.getBoolean(Constants.CONST_IS_TOUR , false)) {
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }else {
                    startActivity(Intent(this, LanguageSelectionActivity::class.java))
                    finish()
                }
            }
        }, 3000)
    }
}