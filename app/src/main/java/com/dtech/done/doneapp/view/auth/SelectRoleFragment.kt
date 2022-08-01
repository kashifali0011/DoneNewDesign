package com.dtech.done.doneapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.FragmentSelectRoleBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.view.base.BaseFragment

class SelectRoleFragment: BaseFragment(),View.OnClickListener  {
    lateinit var binding: FragmentSelectRoleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_role, container, false)
        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.cvCustomer.setOnClickListener(this)
        binding.cvProvider.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.cvCustomer -> {
                if (!DoneApp.db.getString(Constants.CONST_USER_PHONE).isNullOrEmpty()) {
                    addFragment(R.id.authContainer, LoginToYourAccountFragment.newInstance(false,DoneApp.db.getString(Constants.CONST_USER_PHONE)!! , "+966"), "LoginToYourAccountFragment")
                }else {
                    addFragment(R.id.authContainer, CreateAccountFragment.newInstance(false), "LoginToYourAccountFragment")
                }
            }
            R.id.cvProvider -> {
                if (!DoneApp.db.getString(Constants.CONST_USER_PHONE).isNullOrEmpty()) {
                    addFragment(R.id.authContainer, LoginToYourAccountFragment.newInstance(true,DoneApp.db.getString(Constants.CONST_USER_PHONE)!! , "+966"), "LoginToYourAccountFragment")
                }else {
                    addFragment(R.id.authContainer, CreateAccountFragment.newInstance(true), "LoginToYourAccountFragment")
                }

            }
        }
    }
}