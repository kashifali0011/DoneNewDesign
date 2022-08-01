package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentNoProviderBinding
import com.dtech.done.doneapp.utilities.extensions.rejectDialogClickListner
import com.dtech.done.doneapp.utilities.extensions.rejectProviderDialog
import com.dtech.done.doneapp.utilities.extensions.showAppLoader
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.FailedOrdersListAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.NoProviderListAdapter

class NoProviderFragment: BaseFragment() {
    lateinit var binding:FragmentNoProviderBinding
    private var noProviderListAdapter: NoProviderListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_no_provider, container, false)
       // setListener()
        setToolbar()
        setData()
        return binding.root
    }

    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Provider List", null, ""))
    }
    private fun setData(){
        setRvNoProviderList()
    }
    private fun setRvNoProviderList(){
        binding.rvProviderDetailList.apply {
            layoutManager = LinearLayoutManager(ActivityBase.activity)
            noProviderListAdapter = NoProviderListAdapter(ActivityBase.activity,object :NoProviderListAdapter.OnClickListener{
                override fun reject() {
                    ActivityBase.activity.rejectProviderDialog(object :rejectDialogClickListner{
                        override fun yesBtn() {
                            addFragment(R.id.mainContainer, NotificationFragment(),"NotificationFragment")
                        }
                    })
                }
                override fun accept() {
                    addFragment(R.id.mainContainer,ProviderDetailFragment(),"ProviderDetailFragment")
                }
            })
            binding.rvProviderDetailList.adapter = noProviderListAdapter
            noProviderListAdapter!!.notifyDataSetChanged()
        }
    }
}