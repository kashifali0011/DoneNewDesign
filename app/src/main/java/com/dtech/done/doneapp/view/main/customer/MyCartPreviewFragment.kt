package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentMyCartPreviewBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.MyCartAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.MyCartPreviewAdapter

class MyCartPreviewFragment: BaseFragment(), View.OnClickListener{
    lateinit var binding: FragmentMyCartPreviewBinding
    var myCartPreviewAdapter:MyCartPreviewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart_preview,container,false)
        setListener()
        setToolbar()
        getMyServicesPreview()
        return binding.root
    }
    private fun setListener(){
      binding.rbServicesOnDelivery.setOnClickListener(this)
      binding.rbServicesOnCredit.setOnClickListener(this)
      binding.btnOrderPlace.setOnClickListener(this)
    }

    private fun getMyServicesPreview(){
        val bannerLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        binding.rvMyServicesPreview.layoutManager = bannerLayoutManager
        myCartPreviewAdapter = MyCartPreviewAdapter(requireActivity())
        binding.rvMyServicesPreview.adapter =  myCartPreviewAdapter
    }

    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.rbServicesOnDelivery ->{
               binding.rbServicesOnDelivery.isChecked = true
               binding.rbServicesOnCredit.isChecked = false
               validateInput(false)

           }
           R.id.rbServicesOnCredit ->{
               binding.rbServicesOnDelivery.isChecked = false
               binding.rbServicesOnCredit.isChecked = true
               validateInput(false)
           }
           R.id.btnOrderPlace ->{
               if (validateInput(true))
               addFragment(R.id.mainContainer, SuccessOrderPlaceFragment(), "SuccessOrderPlaceFragment")
               else
                   requireActivity().showToastMessage("Please select payment method")
           }
       }
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Checkout", null, ""))
    }
    private fun validateInput(isFromButton : Boolean) : Boolean{
        if (binding.rbServicesOnCredit.isChecked || binding.rbServicesOnDelivery.isChecked){
            binding.btnOrderPlace.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            return true
        }
        binding.btnOrderPlace.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        return false
    }
}


