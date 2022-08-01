package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterAcceptedOrderListBinding
import com.dtech.done.doneapp.databinding.AdapterCompletedOrdersListBinding
import com.dtech.done.doneapp.databinding.AdapterFailedOrdersListBinding

class FailedOrdersListAdapter (val context : Context) : RecyclerView.Adapter<FailedOrdersListAdapter.ViewHolder>() {
    lateinit var binding: AdapterFailedOrdersListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_failed_orders_list ,parent, false)
        return ViewHolder(binding = binding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }
    class ViewHolder(var binding: AdapterFailedOrdersListBinding) :
        RecyclerView.ViewHolder(binding.root)
}