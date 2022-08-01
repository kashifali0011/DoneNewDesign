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
import com.dtech.done.doneapp.data.model.requestmodel.BasicInfoRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.LoginRequestModel
import com.dtech.done.doneapp.databinding.FragmentCreateProfileBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.utilities.extensions.isEmailValid
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.utilities.extensions.webViewDialogBox
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.viewmodel.AuthViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_webview.*


class CreateProfileFragment: BaseFragment(),View.OnClickListener,TextWatcher, View.OnKeyListener, View.OnFocusChangeListener  {
    lateinit var binding: FragmentCreateProfileBinding
    private lateinit var authViewModel: AuthViewModel

    companion object {
        lateinit var instance: CreateProfileFragment
        var phoneNumber: String = ""
        var countryCode: String = ""


        fun newInstance( phoneNumber: String, countryCode: String): CreateProfileFragment {
            instance = CreateProfileFragment()
            this.phoneNumber = phoneNumber
            this.countryCode = countryCode
            return instance
        }

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_profile,container,false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
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

    private fun setListener(){
       binding.btnCreteAccount.setOnClickListener(this)

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
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvTermsAndConditions.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tvPrivacyPolicy ->{
                ActivityBase.activity.webViewDialogBox(Constants.PRIVACY_URL_EN)
            }
            R.id.tvTermsAndConditions ->{
                ActivityBase.activity.webViewDialogBox(Constants.TERMS_URL_EN)
            }
            R.id.btnCreteAccount ->{
                if (validateInput())
                    callBasicProfile()
              /*  startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()*/
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

    }
    private fun buttonChangeColor(){
       if(binding.edtEmailAddress.text!!.isNotEmpty() && binding.edtFullName.text!!.isNotEmpty() &&
           binding.et1.text!!.isNotEmpty() && binding.et2.text!!.isNotEmpty() && binding.et3.text!!.isNotEmpty() && binding.et4.text!!.isNotEmpty()){
           binding.btnCreteAccount.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
       }else{
           binding.btnCreteAccount.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
       }
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
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.et2 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.et3 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                }
            }
            R.id.et4 -> {
                if (hasFocus) {
                    binding.et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                    binding.et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                }
            }
        }
    }

    private fun callBasicProfile(){
        var pin = binding.et1.text.toString()+binding.et2.text.toString()+binding.et3.text.toString()+binding.et4.text.toString()
        authViewModel.basicInfo(BasicInfoRequestModel(binding.edtFullName.text.toString(),binding.edtEmailAddress.text.toString(), pin, countryCode+ phoneNumber))
    }

    private fun validateInput() : Boolean{
        if (binding.edtFullName.text.toString().isEmpty()){
            ActivityBase.activity.showToastMessage(getString(R.string.name_is_required))
            binding.edtFullName.requestFocus()
            return false
        }else if (binding.edtEmailAddress.text.toString().isEmpty()){

            ActivityBase.activity.showToastMessage(getString(R.string.email_is_required))
            binding.edtEmailAddress.requestFocus()
            return false
        }else if (!ActivityBase.activity.isEmailValid(binding.edtEmailAddress.text.toString())){
            ActivityBase.activity.showToastMessage(getString(R.string.please_enter_valid_email))
            return false
        }else if (binding.et1.text.toString().isEmpty()){
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
        } else if (!binding.rbAgreeWithPrivacy.isChecked){
            ActivityBase.activity.showToastMessage(getString(R.string.please_select_you_agree))
            return false
        }
        return true
    }

    private fun callLogin(){
        var pin = binding.et1.text.toString()+binding.et2.text.toString()+binding.et3.text.toString()+binding.et4.text.toString()
        authViewModel.login(LoginRequestModel(countryCode + phoneNumber ,pin,UserType.CUSTOMER.value,DoneApp.db.getString(Constants.DEVICE_ID,"")!!))
    }




}