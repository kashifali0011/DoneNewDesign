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
import com.dtech.done.doneapp.data.model.requestmodel.CreatePinRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.LoginRequestModel
import com.dtech.done.doneapp.databinding.FragmentForgotPinBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.viewmodel.AuthViewModel
import com.google.gson.Gson

class ForgotPinFragment : BaseFragment(),  View.OnClickListener, TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    lateinit var binding: FragmentForgotPinBinding
    private lateinit var authViewModel: AuthViewModel

    companion object {
        lateinit var instance: ForgotPinFragment
        private var isProvider: Boolean = false
        private var isCreatePin : Boolean = false
        var phoneNumber: String = ""
        var countryCode: String = ""


        fun newInstance(isProvider: Boolean , isCreatePin : Boolean, phoneNumber: String, countryCode: String): ForgotPinFragment{
            instance = ForgotPinFragment()
            this.isProvider = isProvider
            this.isCreatePin = isCreatePin
            this.phoneNumber = phoneNumber
            this.countryCode = countryCode
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_pin, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.isCreatePin = isCreatePin
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

        authViewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            callLogin()
        })
        authViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                DoneApp.db.putString(Constants.CONST_USER_TYPE, it!!.userType!!)
                DoneApp.db.putString(Constants.CONST_USER_DATA, Gson().toJson(it!!))
                DoneApp.db.putBoolean(Constants.CONST_IS_LOGIN, true)
                DoneApp.db.putString(Constants.CONST_USER_PHONE, LoginToYourAccountFragment.phoneNumber)
                if (it.token != null) {
                    DoneApp.db.putString(Constants.ACCESS_TOKEN, it.token!!.accessToken!!)
                    DoneApp.db.putString(Constants.TOKEN_TYPE, it.token!!.tokenType!!)
                }
                startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()
            }
        })
    }

    private fun setListener() {
        binding.btnUpdatePin.setOnClickListener(this)
        binding.etCreatePin1.addTextChangedListener(this)
        binding.etCreatePin2.addTextChangedListener(this)
        binding.etCreatePin3.addTextChangedListener(this)
        binding.etCreatePin4.addTextChangedListener(this)
        binding.etConfirmPin1.addTextChangedListener(this)
        binding.etConfirmPin2.addTextChangedListener(this)
        binding.etConfirmPin3.addTextChangedListener(this)
        binding.etConfirmPin4.addTextChangedListener(this)
        binding.etCreatePin1.setOnKeyListener(this)
        binding.etCreatePin2.setOnKeyListener(this)
        binding.etCreatePin3.setOnKeyListener(this)
        binding.etCreatePin4.setOnKeyListener(this)
        binding.etConfirmPin1.setOnKeyListener(this)
        binding.etConfirmPin2.setOnKeyListener(this)
        binding.etConfirmPin3.setOnKeyListener(this)
        binding.etConfirmPin4.setOnKeyListener(this)
        binding.etCreatePin1.onFocusChangeListener = this
        binding.etCreatePin2.onFocusChangeListener = this
        binding.etCreatePin3.onFocusChangeListener = this
        binding.etCreatePin4.onFocusChangeListener = this
        binding.etConfirmPin1.onFocusChangeListener = this
        binding.etConfirmPin2.onFocusChangeListener = this
        binding.etConfirmPin3.onFocusChangeListener = this
        binding.etConfirmPin4.onFocusChangeListener = this
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnUpdatePin -> {
                if (validateInput()) {
                    callCreatePin()
                }
                //ActivityBase.activity.supportFragmentManager.popBackStackImmediate()
            }
        }
    }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        //create pin
        if (binding.etCreatePin1.hasFocus()) {
            if (binding.etCreatePin1.text.toString().length == 1) {
                binding.etCreatePin1.clearFocus()
                binding.etCreatePin2.requestFocus()
            }
        } else if (binding.etCreatePin2.hasFocus()) {
            if (binding.etCreatePin2.text.toString().length == 1) {
                binding.etCreatePin2.clearFocus()
                binding.etCreatePin3.requestFocus()
            }

        } else if (binding.etCreatePin3.hasFocus()) {
            if (binding.etCreatePin3.text.toString().length == 1) {
                binding.etCreatePin3.clearFocus()
                binding.etCreatePin4.requestFocus()
            }

        } else if (binding.etCreatePin4.hasFocus()) {
            if (binding.etCreatePin4.text.toString().length == 1) {
                binding.etCreatePin1.clearFocus()
                binding.etCreatePin2.clearFocus()
                binding.etCreatePin3.clearFocus()
                binding.etCreatePin4.clearFocus()
                binding.etConfirmPin1.requestFocus()

            }
        }else if (binding.etConfirmPin1.hasFocus()) {
            if (binding.etConfirmPin1.text.toString().length == 1) {
                binding.etConfirmPin1.clearFocus()
                binding.etConfirmPin2.requestFocus()
            }
        } else if (binding.etConfirmPin2.hasFocus()) {
            if (binding.etConfirmPin2.text.toString().length == 1) {
                binding.etConfirmPin2.clearFocus()
                binding.etConfirmPin3.requestFocus()
            }

        } else if (binding.etConfirmPin3.hasFocus()) {
            if (binding.etConfirmPin3.text.toString().length == 1) {
                binding.etConfirmPin3.clearFocus()
                binding.etConfirmPin4.requestFocus()
            }

        } else if (binding.etConfirmPin4.hasFocus()) {
            if (binding.etConfirmPin4.text.toString().length == 1) {
                binding.etConfirmPin1.clearFocus()
                binding.etConfirmPin2.clearFocus()
                binding.etConfirmPin3.clearFocus()
                binding.etConfirmPin4.clearFocus()
                //ApiCAll
                binding.root.hideKeyboard()

            }
        }
        buttonChangeColor()
    }

    private fun buttonChangeColor(){
        if(
            binding.etCreatePin1.text!!.isNotEmpty() && binding.etCreatePin2.text!!.isNotEmpty() &&
            binding.etCreatePin3.text!!.isNotEmpty() && binding.etCreatePin4.text!!.isNotEmpty() &&
            binding.etConfirmPin1.text!!.isNotEmpty() && binding.etConfirmPin2.text!!.isNotEmpty() &&
            binding.etConfirmPin3.text!!.isNotEmpty() && binding.etConfirmPin4.text!!.isNotEmpty()
        )
        {
            binding.btnUpdatePin.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        }else{
            binding.btnUpdatePin.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL)
            when (v!!.id) {
                R.id.etCreatePin1 -> {

                }
                R.id.etCreatePin2 -> {
                    if (TextUtils.isEmpty(binding.etCreatePin2.text.toString())) {
                        binding.etCreatePin2.clearFocus()
                        binding.etCreatePin1.requestFocus()
                    } else {
                        binding.etCreatePin2.setText("")
                    }
                }
                R.id.etCreatePin3 -> {
                    if (TextUtils.isEmpty(binding.etCreatePin3.text.toString())) {
                        binding.etCreatePin3.clearFocus()
                        binding.etCreatePin2.requestFocus()
                    } else {
                        binding.etCreatePin3.setText("")
                    }
                }
                R.id.etCreatePin4 -> {
                    if (TextUtils.isEmpty(binding.etCreatePin4.text.toString())) {
                        binding.etCreatePin4.clearFocus()
                        binding.etCreatePin3.requestFocus()
                    } else {
                        binding.etCreatePin4.setText("")
                    }
                }
                //confirm pin
                R.id.etConfirmPin1 -> {

                }
                R.id.etConfirmPin2 -> {
                    if (TextUtils.isEmpty(binding.etConfirmPin2.text.toString())) {
                        binding.etConfirmPin2.clearFocus()
                        binding.etConfirmPin1.requestFocus()
                    } else {
                        binding.etConfirmPin2.setText("")
                    }
                }
                R.id.etConfirmPin3 -> {
                    if (TextUtils.isEmpty(binding.etConfirmPin3.text.toString())) {
                        binding.etConfirmPin3.clearFocus()
                        binding.etConfirmPin2.requestFocus()
                    } else {
                        binding.etConfirmPin3.setText("")
                    }
                }
                R.id.etConfirmPin4 -> {
                    if (TextUtils.isEmpty(binding.etConfirmPin4.text.toString())) {
                        binding.etConfirmPin4.clearFocus()
                        binding.etConfirmPin3.requestFocus()
                    } else {
                        binding.etConfirmPin4.setText("")
                    }
                }
            }

        return false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.etCreatePin1 -> {
                if (hasFocus) {
                    binding.etCreatePin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etCreatePin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etCreatePin2 -> {
                if (hasFocus) {
                    binding.etCreatePin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etCreatePin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etCreatePin3 -> {
                if (hasFocus) {
                    binding.etCreatePin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etCreatePin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etCreatePin4 -> {
                if (hasFocus) {
                    binding.etCreatePin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etCreatePin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                }
            }
            //confirm pin
            R.id.etConfirmPin1 -> {
                if (hasFocus) {
                    binding.etConfirmPin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etConfirmPin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etConfirmPin2 -> {
                if (hasFocus) {
                    binding.etConfirmPin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etConfirmPin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etConfirmPin3 -> {
                if (hasFocus) {
                    binding.etConfirmPin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.etConfirmPin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.etConfirmPin4 -> {
                if (hasFocus) {
                    binding.etConfirmPin1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.etConfirmPin4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                }
            }
        }
    }

    private fun callCreatePin(){
        var pin = binding.etCreatePin1.text.toString()+binding.etCreatePin2.text.toString()+binding.etCreatePin3.text.toString()+binding.etCreatePin4.text.toString()
        authViewModel.createPin(CreatePinRequestModel(countryCode+ phoneNumber,pin))
    }

    private fun validateInput() : Boolean{
        var pin = binding.etCreatePin1.text.toString()+binding.etCreatePin2.text.toString()+binding.etCreatePin3.text.toString()+binding.etCreatePin4.text.toString()
        var confirmPin = binding.etConfirmPin1.text.toString()+binding.etConfirmPin2.text.toString()+binding.etConfirmPin3.text.toString()+binding.etConfirmPin4.text.toString()

        if (binding.etCreatePin1.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etCreatePin1.requestFocus()
            return false
        } else   if (binding.etCreatePin2.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etCreatePin2.requestFocus()
            return false
        }else   if (binding.etCreatePin3.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etCreatePin3.requestFocus()
            return false
        }else   if (binding.etCreatePin4.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etCreatePin4.requestFocus()
            return false
        }else   if (binding.etConfirmPin1.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etConfirmPin1.requestFocus()
            return false
        }else   if (binding.etConfirmPin2.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etConfirmPin2.requestFocus()
            return false
        }else   if (binding.etConfirmPin3.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etConfirmPin3.requestFocus()
            return false
        }else   if (binding.etConfirmPin4.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.this_field_is_required))
            binding.etConfirmPin4.requestFocus()
            return false
        }else if (!pin.equals(confirmPin)){
            ActivityBase.activity.showToastMessage(getString(R.string.create_and_confirm_pin_code_does_not_match))
            binding.etConfirmPin1.setText("")
            binding.etConfirmPin2.setText("")
            binding.etConfirmPin3.setText("")
            binding.etConfirmPin4.setText("")
            binding.etCreatePin1.setText("")
            binding.etCreatePin2.setText("")
            binding.etCreatePin3.setText("")
            binding.etCreatePin4.setText("")
            binding.etCreatePin1.requestFocus()
            return false
        }
        return true
    }

    private fun callLogin(){
        var pin = binding.etCreatePin1.text.toString()+binding.etCreatePin2.text.toString()+binding.etCreatePin3.text.toString()+binding.etCreatePin4.text.toString()
        authViewModel.login(LoginRequestModel(countryCode + phoneNumber ,pin, if (isProvider) UserType.PROVIDER.value else UserType.CUSTOMER.value, DoneApp.db.getString(Constants.DEVICE_ID,"")!!))
    }
}