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
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.FragmentLoginWithDifferentAccountBinding
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class LoginWithDifferentAccountFragment : BaseFragment(), View.OnClickListener, TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    lateinit var binding: FragmentLoginWithDifferentAccountBinding
    companion object {
        lateinit var instance: LoginWithDifferentAccountFragment
        private var isProvider: Boolean = false

        //// not used this fragment


        fun newInstance(isProvider: Boolean): LoginWithDifferentAccountFragment{
            instance = LoginWithDifferentAccountFragment()
            this.isProvider = isProvider
            return instance
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_with_different_account, container, false)
        binding.isProvider = isProvider
        setListener()
        return binding.root
    }

    private fun setListener(){
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
        binding.btnProceed.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.tvSignUp -> {
                addFragment(R.id.authContainer , CreateAccountFragment() , "CreateAccountFragment")
            }
            R.id.tvForgot -> {
                addFragment(R.id.authContainer,ForgotPinFragment(),"ForgotPinFragment")
            }

            R.id.btnProceed -> {
                startActivity(Intent(ActivityBase.activity , MainActivity::class.java))
                ActivityBase.activity.finish()
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
                //ApiCAll
                binding.root.hideKeyboard()

                /* if (!isApiCall)
                     verifyCode()
                 else{
                     binding.root.hideKeyboard()
                 }*/
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
}