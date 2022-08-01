package com.dtech.done.doneapp.view.auth

import android.os.Bundle
import android.os.CountDownTimer
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
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.requestmodel.ForgotPinRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.VerifyOtpRequestModel

import com.dtech.done.doneapp.databinding.FragmentVerifyOtpBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.viewmodel.AuthViewModel

class VerifyOtpFragment : BaseFragment(), View.OnClickListener, TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {

    lateinit var binding: FragmentVerifyOtpBinding
    private lateinit var authViewModel: AuthViewModel
    private var countDownTimer: CountDownTimer? = null

    companion object {
        lateinit var instance: VerifyOtpFragment
        private var isProvider: Boolean = false
        private var isCreatePin: Boolean = false
        var phoneNumber: String = ""
        var countryCode: String = ""


        fun newInstance(isProvider: Boolean, phoneNumber: String, isCreatePin: Boolean, countryCode: String): VerifyOtpFragment {
            instance = VerifyOtpFragment()
            this.isProvider = isProvider
            this.phoneNumber = phoneNumber
            this.isCreatePin = isCreatePin
            this.countryCode = countryCode
            return instance
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_otp, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        setUiObserver()
        setListener()
        startTimer()
        return binding.root
    }

    private fun setUiObserver() {
        authViewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
                binding.et1.setText("")
                binding.et2.setText("")
                binding.et3.setText("")
                binding.et4.setText("")
            }
        })

        authViewModel.verifyOtpResponse.observe(viewLifecycleOwner, Observer {
            moveNextFragment(it.step)
        })

        authViewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            if (countDownTimer != null)
                countDownTimer!!.cancel()
            startTimer()
        })
    }


    private fun setListener() {
        binding.tvResendCode.setOnClickListener(this)
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
        when (v!!.id) {
            R.id.tvResendCode -> {
                binding.et1.setText("")
                binding.et2.setText("")
                binding.et3.setText("")
                binding.et4.setText("")

                authViewModel.resendOtp(ForgotPinRequestModel(countryCode + phoneNumber))
                binding.tvResendCode.visibility = View.GONE
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
                if (validateInput()) {
                    callVerifyOtp()
                }
            }
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

    private fun callVerifyOtp() {
        authViewModel.verifyOtp(VerifyOtpRequestModel(countryCode + phoneNumber, binding.et1.text.toString() + binding.et2.text.toString() + binding.et3.text.toString() + binding.et4.text.toString(), if (isCreatePin) null else true))
    }

    private fun moveNextFragment(step: Int) {

        when (step) {
            Constants.CONST_NEW_USER -> {
                addFragment(R.id.authContainer, CreateProfileFragment.newInstance(phoneNumber, countryCode), null)
            }
            Constants.CONST_SET_PIN -> {
                addFragment(R.id.authContainer, ForgotPinFragment.newInstance(isProvider, isCreatePin, phoneNumber, countryCode), "ForgotPinFragment")
            }

            Constants.CONST_OLD_USER -> {
                addFragment(R.id.authContainer, LoginToYourAccountFragment.newInstance(isProvider, phoneNumber, countryCode), "LoginToYourAccountFragment")
            }
        }

    }

    private fun validateInput(): Boolean {
        if (binding.et1.text.toString().isEmpty()) {
            val validateMsg = getString(R.string.this_field_is_required)
            requireActivity().showToastMessage(validateMsg)
            binding.et1.requestFocus()
            return false
        } else if (binding.et2.text.toString().isEmpty()) {
            val validateMsg = getString(R.string.this_field_is_required)
            requireActivity().showToastMessage(validateMsg)
            binding.et2.requestFocus()
            return false
        } else if (binding.et3.text.toString().isEmpty()) {
            val validateMsg = getString(R.string.this_field_is_required)
            requireActivity().showToastMessage(validateMsg)
            binding.et3.requestFocus()
            return false
        } else if (binding.et4.text.toString().isEmpty()) {
            val validateMsg = getString(R.string.this_field_is_required)
            requireActivity().showToastMessage(validateMsg)
            binding.et4.requestFocus()
            return false
        }
        return true
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer((60 * 1 * 1000).toLong(), 100) {
            override fun onTick(l: Long) {
                val seconds = l / 1000
                val currentMinute = seconds / 60
                binding.tvTimer.text = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60)
                if (currentMinute == 0L && seconds == 0L) {
                    binding.tvResendCode.visibility = View.VISIBLE
                    binding.tvTimer.text = "00:00"
                    if (countDownTimer != null) {
                        countDownTimer!!.cancel()
                    }
                } else {

                }
            }

            override fun onFinish() {
                binding.tvResendCode.visibility = View.VISIBLE
                binding.tvTimer.text = "00:00"
                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                }
            }

        }.start()
    }

}