package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentSuccessOrderPlaceBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class SuccessOrderPlaceFragment: BaseFragment(),View.OnClickListener{
    lateinit var binding: FragmentSuccessOrderPlaceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success_order_place,container,false)
        setListener()
        setToolbar()
        return binding.root
    }

    private fun setListener(){

    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Order Placed", null, ""))
    }
}