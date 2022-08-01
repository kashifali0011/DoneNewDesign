package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterAcceptedOrderListBinding
import com.dtech.done.doneapp.databinding.AdapterImageSelectionBinding

class AcceptedOrdersListAdapter(val context : Context) : RecyclerView.Adapter<AcceptedOrdersListAdapter.ViewHolder>() {
    lateinit var binding: AdapterAcceptedOrderListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding  = AdapterAcceptedOrderListBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_accepted_order_list, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }
    class ViewHolder(var binding: AdapterAcceptedOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)
}