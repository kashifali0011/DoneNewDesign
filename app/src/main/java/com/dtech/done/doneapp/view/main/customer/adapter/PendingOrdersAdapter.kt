package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterFailedOrdersListBinding
import com.dtech.done.doneapp.databinding.AdapterPendingOrderListBinding

class PendingOrdersAdapter(val context : Context) : RecyclerView.Adapter<PendingOrdersAdapter.ViewHolder>() {
    lateinit var binding:AdapterPendingOrderListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_pending_order_list ,parent, false)
        return ViewHolder(binding = binding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(var binding: AdapterPendingOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)
}