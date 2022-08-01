package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveNotificationModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentNotificationBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.NotificationAdapter

class NotificationFragment : BaseFragment() {

    lateinit var binding: FragmentNotificationBinding
    var notificationAdapter: NotificationAdapter? = null
    val arrayList: ArrayList<SaveNotificationModel> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        setToolbar()
        setListener()
        getNotification()
        return binding.root
    }

    private fun setListener() {
    }

    private fun getNotification() {
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        arrayList.add(SaveNotificationModel(1))
        val notificationLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvNotification.layoutManager = notificationLayoutManager
        notificationAdapter = NotificationAdapter(requireActivity(),arrayList,
            object : NotificationAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                addFragment(R.id.mainContainer , NoProviderFragment() , "NoProviderFragment")
            }
        })
        binding.rvNotification.adapter = notificationAdapter
    }

    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(isNavigationShown = false, isBackShown = true, placesShown = false, notificationShown = false, cartShown = false, userName = "", title = "Notifications", subTitle = null, placeName = ""))
    }
}