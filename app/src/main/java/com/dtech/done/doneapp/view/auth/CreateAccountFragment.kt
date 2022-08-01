package com.dtech.done.doneapp.view.auth

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.requestmodel.VerifyPhoneRequestModel
import com.dtech.done.doneapp.databinding.FragmentCreateAccountBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.viewmodel.AuthViewModel

class CreateAccountFragment : BaseFragment(), View.OnClickListener, TextWatcher {
    lateinit var binding: FragmentCreateAccountBinding
    private lateinit var authViewModel: AuthViewModel

    companion object {
        lateinit var instance: CreateAccountFragment
        private var isProvider: Boolean = false


        fun newInstance(isProvider: Boolean): CreateAccountFragment{
            instance = CreateAccountFragment()
            this.isProvider = isProvider
            return instance
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
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

        authViewModel.generalResponse.observe(viewLifecycleOwner, Observer {

            addFragment(R.id.authContainer , VerifyOtpFragment.newInstance(isProvider,binding.etPhone.text.toString(),true,"+966") , null)
        })
    }

    private fun setListener() {
        binding.tvLogIn.setOnClickListener(this)
        binding.btnProceed.setOnClickListener(this)
        binding.btnContinueAsGuest.setOnClickListener(this)
        binding.etPhone.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLogIn -> {
                ActivityBase.activity.supportFragmentManager.popBackStackImmediate()
            }

            R.id.btnProceed -> {
                if (validateInput(true))
                    callVerifyPhone()
              //  addFragment(R.id.authContainer , VerifyOtpFragment.newInstance(isProvider) , "VerifyOtpFragment")
            }
            R.id.btnContinueAsGuest -> {
                DoneApp.db.putString(Constants.CONST_USER_TYPE, UserType.GUEST.value.toString())
                DoneApp.db.putBoolean(Constants.CONST_IS_LOGIN, true)
                startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()
            }
        }
    }

    private fun validateInput(isFromButton : Boolean) : Boolean{
        if (binding.etPhone.text.toString().length < 9){
            if (isFromButton){
                requireActivity().showToastMessage("The phone number must have 9 digits")
            }
         //   binding.btnProceed.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
            return false
        }
//        binding.btnProceed.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        return true
    }
    private fun callVerifyPhone(){
        authViewModel.verifyPhone(VerifyPhoneRequestModel("+966",binding.etPhone.text.toString(),if (isProvider) UserType.PROVIDER.value else UserType.CUSTOMER.value))
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        super.beforeTextChanged(p0, p1, p2, p3)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        super.onTextChanged(p0, p1, p2, p3)
    }

    override fun afterTextChanged(p0: Editable?) {
        validateInput(false)
        super.afterTextChanged(p0)
    }
}