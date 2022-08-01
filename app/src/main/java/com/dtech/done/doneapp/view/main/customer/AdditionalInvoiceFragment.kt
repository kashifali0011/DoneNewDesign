package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentAdditionalInvoiceBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.AddAddressBottomSheetFragment
import com.dtech.done.doneapp.view.bottomsheet.AdditionalInvoiceBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity

class AdditionalInvoiceFragment: BaseFragment(), View.OnClickListener {
    lateinit var binding:FragmentAdditionalInvoiceBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_additional_invoice, container, false)
        setToolbar()
        setListener()
        return binding.root
    }
    fun setListener(){
        binding.cvAcceptBtn.setOnClickListener(this)
    }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Additional Invoice", null, ""))
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.cvAcceptBtn ->{
                showAdditionalInvoiceBottomSheetDialog()
            }
        }
    }
    private fun showAdditionalInvoiceBottomSheetDialog() {
        var  bottomSheetListFragment = AdditionalInvoiceBottomSheetFragment()
        if (!bottomSheetListFragment.isAdded) {
            bottomSheetListFragment.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }
}