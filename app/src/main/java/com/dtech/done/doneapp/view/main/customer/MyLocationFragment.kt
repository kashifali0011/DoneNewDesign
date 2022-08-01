package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentMyLocationBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class MyLocationFragment:BaseFragment(), View.OnClickListener {

    lateinit var binding:FragmentMyLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_location, container, false)
        setToolbar()
        setListener()
        return binding.root
    }
    private fun setListener() {
        binding.ivCross.setOnClickListener(this)
        binding.btnAddLocation.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivCross -> {
              binding.clMiniAddressNotification.visibility = View.GONE
            }
            R.id.btnAddLocation ->{
                addFragment(R.id.mainContainer , SelectLocationFragment("",0.0,0.0,"","","","",false) , "SelectLocationFragment")

            }
        }
    }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "My Locations", null, ""))
    }


}