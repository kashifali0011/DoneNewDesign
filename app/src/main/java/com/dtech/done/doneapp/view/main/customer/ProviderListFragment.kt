package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ProviderOptionList
import com.dtech.done.doneapp.data.model.custommodel.SaveLocationStatusModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.SaveAddressRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderData
import com.dtech.done.doneapp.data.model.responsemodel.Providers
import com.dtech.done.doneapp.databinding.FragmentProvidersListBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.utilities.utils.DateUtil
import com.dtech.done.doneapp.view.auth.CreateAccountFragment
import com.dtech.done.doneapp.view.auth.VerifyOtpFragment
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.AddAddressBottomSheetFragment
import com.dtech.done.doneapp.view.bottomsheet.BottomSheetDateTimePicker
import com.dtech.done.doneapp.view.bottomsheet.MediaBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.ProviderListAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.SearchItemsListAdapter
import com.dtech.done.doneapp.viewmodel.ProviderViewModel

class ProviderListFragment : BaseFragment(), View.OnClickListener,
    MediaBottomSheetFragment.ISelectListener,
    ProviderListAdapter.OnItemClickListener,
    BottomSheetDateTimePicker.ISelectDobListener {
    lateinit var binding: FragmentProvidersListBinding
    private lateinit var providerViewModel: ProviderViewModel
    var providerListAdapter: ProviderListAdapter? = null

    var providerList:ArrayList<Providers> = ArrayList()

    var lowToHigh = 0
    var highToLow = 0
    var selectDate = ""
    var selectTime = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_providers_list, container, false)
        providerViewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)
        setUiObserver()
        getProvider()
        setToolbar()
        setListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvOfProviderList()
    }

    private fun setUiObserver() {
        providerViewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })

        providerViewModel.getProviderList.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                if (it.providers != null){
                    providerList = it.providers
                    setRvOfProviderList()
                }
            }
        })

    }

    private fun setRvOfProviderList() {
        val categoryLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvProviderDetailList.layoutManager = categoryLayoutManager
        providerListAdapter = ProviderListAdapter(requireActivity(),providerList,this)
        binding.rvProviderDetailList.adapter = providerListAdapter

    }

    private fun setListener() {
        binding.llSortedDropList.setOnClickListener(this)
        binding.llDate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llSortedDropList -> {
                showBottomSheet()
            }
            R.id.llDate -> {
                showBottomSheetDateAndTime()
            }
        }
    }

    private fun showBottomSheet() {
        var bottomSheetListFragment = MediaBottomSheetFragment(arrayListOf(getString(R.string.low_price), getString(R.string.high_to_low)), getString(R.string.cancel))
        bottomSheetListFragment.setMyListener(this)
        if (!bottomSheetListFragment.isAdded) {
            bottomSheetListFragment.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }

    private fun showBottomSheetDateAndTime() {
        var bottomSheetDateTimePicker = BottomSheetDateTimePicker("", "")
        bottomSheetDateTimePicker.setMyListener(this)
        if (!bottomSheetDateTimePicker.isAdded) {
            bottomSheetDateTimePicker.show(ActivityBase.activity.supportFragmentManager, "")
        }

    }

    override fun onMediaSelect(pos: Int) {
        if (pos == 0) {
            binding.tvSelectPriceStatus.text = getString(R.string.low_price)
            lowToHigh = 1
            highToLow = 0
        } else {
            binding.tvSelectPriceStatus.text = getString(R.string.high_to_low)
            lowToHigh = 0
            highToLow = 1
        }
        getProvider()
    }

    override fun onMediaCancel() {
    }

    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Provider List", null, ""))
    }

    private fun getProvider() {
        var myList: ArrayList<Int> = ArrayList()
    /*    myList.add(1)
        myList.add(2)*/
        providerViewModel.getProvidersList(GetProviderRequestModel(selectDate, lowToHigh, highToLow, "1.1", 31.5023513, "1", 1, myList, selectTime, 74.3230671))
    }

    override fun onSelectDateAndTime(date: String, startTime: String) {
        selectDate = date
        selectTime = DateUtil.convertSingleTimePicker(startTime)
        binding.tvSelectDateAndTime.text = "$selectDate $selectTime"
        getProvider()

    }

    override fun onItemClick(position: Int, serviceId: Int , providerId: Int, rating: String , description: String) {
        addFragment(R.id.mainContainer,ProviderDetailFragment.newInstance(serviceId,providerId,selectDate,selectTime ,rating , description),"ProviderDetailFragment")

    }

}