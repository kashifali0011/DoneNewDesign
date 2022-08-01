package com.dtech.done.doneapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.requestmodel.ForgotPinRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.LoginRequestModel
import com.dtech.done.doneapp.databinding.FragmentLoginToAccountBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.utilities.extensions.isEmailValid
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.viewmodel.AuthViewModel
import com.google.gson.Gson


class LoginToYourAccountFragment : BaseFragment(),View.OnClickListener,TextWatcher, View.OnKeyListener, View.OnFocusChangeListener  {
    lateinit var binding: FragmentLoginToAccountBinding
    private lateinit var authViewModel: AuthViewModel

    companion object {
        lateinit var instance: LoginToYourAccountFragment
        private var isProvider: Boolean = false
        var phoneNumber: String = ""
        var countryCode: String = ""


        fun newInstance(isProvider: Boolean, phoneNumber: String, countryCode: String): LoginToYourAccountFragment{
            instance = LoginToYourAccountFragment()
            this.isProvider = isProvider
            this.phoneNumber = phoneNumber
            this.countryCode = countryCode
            return instance
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_to_account, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.isProvider = isProvider
        setUiObserver()
        setListener()
        return binding.root
    }

    private fun setUiObserver() {
        authViewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })


        authViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                DoneApp.db.putString(Constants.CONST_USER_TYPE, it!!.userType!!)
                DoneApp.db.putString(Constants.CONST_USER_DATA, Gson().toJson(it!!))
                DoneApp.db.putBoolean(Constants.CONST_IS_LOGIN, true)
                DoneApp.db.putString(Constants.CONST_USER_PHONE, phoneNumber)
                if (it.token != null) {
                    DoneApp.db.putString(Constants.ACCESS_TOKEN, it.token!!.accessToken!!)
                    DoneApp.db.putString(Constants.TOKEN_TYPE, it.token!!.tokenType!!)
                }
                startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()
            }
        })

        authViewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            addFragment(R.id.authContainer , VerifyOtpFragment.newInstance(isProvider, phoneNumber,false, countryCode) , null)

          //  addFragment(R.id.authContainer,ForgotPinFragment.newInstance(isProvider , false, phoneNumber, countryCode),"ForgotPinFragment")
        })
    }


    private fun setListener(){
        binding.tvLoginWithDifferentAccount.setOnClickListener(this)
        binding.btnContinueAsGuest.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)
        binding.et1.addTextChangedListener(this)
        binding.et2.addTextChangedListener(this)
        binding.et3.addTextChangedListener(this)
        binding.et4.addTextChangedListener(this)
        binding.et1.setOnKeyListener(this)
        binding.et2.setOnKeyListener(this)
        binding.et3.setOnKeyListener(this)
        binding.et4.setOnKeyListener(this)
        binding.et1.onFocusChangeListener = this
        binding.et2.onFocusChangeListener = this
        binding.et3.onFocusChangeListener = this
        binding.et4.onFocusChangeListener = this
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tvLoginWithDifferentAccount ->{

                addFragment(R.id.authContainer, CreateAccountFragment.newInstance(
                    isProvider),"CreateAccountFragment")
            }

            R.id.tvSignUp -> {
                addFragment(R.id.authContainer , CreateAccountFragment.newInstance(isProvider) , "CreateAccountFragment")
            }
            R.id.tvForgot -> {
               callForgotPin()
            }
            R.id.btnContinueAsGuest -> {
                DoneApp.db.putString(Constants.CONST_USER_TYPE, UserType.GUEST.value.toString())
                DoneApp.db.putBoolean(Constants.CONST_IS_LOGIN, true)
                startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()
            }
            R.id.btnLogin -> {
                if (validateInput()){
                    callLogin()
                }
            }
        }
    }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        if (binding.et1.hasFocus()) {
            if (binding.et1.text.toString().length == 1) {
                binding.et1.clearFocus()
                binding.et2.requestFocus()
            }
        } else if (binding.et2.hasFocus()) {
            if (binding.et2.text.toString().length == 1) {
                binding.et2.clearFocus()
                binding.et3.requestFocus()
            }

        } else if (binding.et3.hasFocus()) {
            if (binding.et3.text.toString().length == 1) {
                binding.et3.clearFocus()
                binding.et4.requestFocus()
            }

        } else if (binding.et4.hasFocus()) {
            if (binding.et4.text.toString().length == 1) {
                binding.et1.clearFocus()
                binding.et2.clearFocus()
                binding.et3.clearFocus()
                binding.et4.clearFocus()
                binding.root.hideKeyboard()

            }
        }
        buttonChangeColor()
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL)
            when (v!!.id) {
                R.id.et1 -> {

                }
                R.id.et2 -> {
                    if (TextUtils.isEmpty(binding.et2.text.toString())) {
                        binding.et2.clearFocus()
                        binding.et1.requestFocus()
                    } else {
                        binding.et2.setText("")
                    }
                }
                R.id.et3 -> {
                    if (TextUtils.isEmpty(binding.et3.text.toString())) {
                        binding.et3.clearFocus()
                        binding.et2.requestFocus()
                    } else {
                        binding.et3.setText("")
                    }
                }
                R.id.et4 -> {
                    if (TextUtils.isEmpty(binding.et4.text.toString())) {
                        binding.et4.clearFocus()
                        binding.et3.requestFocus()
                    } else {
                        binding.et4.setText("")
                    }
                }
            }

        return false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.et1 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_blue_bottom_line_)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                }
            }
            R.id.et2 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_blue_bottom_line_)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                }
            }
            R.id.et3 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_blue_bottom_line_)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                }
            }
            R.id.et4 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_bottom_line)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_blue_bottom_line_)
                }
            }
        }
    }

    private fun validateInput() : Boolean{
        if (binding.et1.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.et1.requestFocus()
            return false
        } else   if (binding.et2.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.et2.requestFocus()
            return false
        }else   if (binding.et3.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.et3.requestFocus()
            return false
        }else   if (binding.et4.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.et4.requestFocus()
            return false
        }
        return true
    }

    private fun callLogin(){
        var pin = binding.et1.text.toString()+binding.et2.text.toString()+binding.et3.text.toString()+binding.et4.text.toString()
        authViewModel.login(LoginRequestModel(countryCode + phoneNumber ,pin, if (isProvider) UserType.PROVIDER.value else UserType.CUSTOMER.value,DoneApp.db.getString(Constants.DEVICE_ID,"")!!))
    }


    private fun callForgotPin(){
        authViewModel.forgotPin(ForgotPinRequestModel(countryCode + phoneNumber))
    }


    private fun buttonChangeColor(){
        if(
            binding.et1.text!!.isNotEmpty() && binding.et2.text!!.isNotEmpty() &&
            binding.et3.text!!.isNotEmpty() && binding.et4.text!!.isNotEmpty()
        )
        {
            binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        }else{
            binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
        }
    }


}