package com.dtech.done.doneapp.view.auth

import android.os.Bundle
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.view.base.ActivityBase

class AuthActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        addFragment(R.id.authContainer , SelectRoleFragment() , null)
    }
}