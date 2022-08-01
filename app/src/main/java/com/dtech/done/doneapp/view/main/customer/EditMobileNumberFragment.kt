package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentEditMobileNumberBinding
import com.dtech.done.doneapp.view.auth.VerifyOtpFragment
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class EditMobileNumberFragment: BaseFragment(), View.OnClickListener {
    lateinit var binding:FragmentEditMobileNumberBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_mobile_number, container, false)
        setListener()
        setToolbar()
        return binding.root
    }
    fun setListener(){
        binding.btnVerify.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnVerify ->{
            //    addFragment(R.id.mainContainer,VerifyOtpFragment(),"VerifyOtpFragment")
            }
        }
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Edit Mobile Number", null, ""))
    }


}