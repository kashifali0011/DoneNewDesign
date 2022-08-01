package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentNotifyEmailVerificationBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class NotifyEmailVerificationFragment: BaseFragment(), View.OnClickListener {
    lateinit var binding:FragmentNotifyEmailVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notify_email_verification, container, false)
        setListener()
        setToolbar()
        return binding.root
    }
    fun setListener(){
        binding.btnViewProfile.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnViewProfile ->{
                requireActivity().supportFragmentManager.popBackStackImmediate()
                addFragment(R.id.mainContainer,EditCustomerProfileFragment(),"EditCustomerProfileFragment")
            }
        }
    }

    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "", null, ""))
    }


}