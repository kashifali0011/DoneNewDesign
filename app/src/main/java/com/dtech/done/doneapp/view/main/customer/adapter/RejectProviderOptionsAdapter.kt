package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterRejectProviderOptionsBinding

class RejectProviderOptionsAdapter(val context : Context, var mListener: OnItemClickListener?) : RecyclerView.Adapter<RejectProviderOptionsAdapter.ViewHolder>() {
    lateinit var binding: AdapterRejectProviderOptionsBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding  = AdapterRejectProviderOptionsBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_reject_provider_options, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
       return 5
    }
    class ViewHolder(var binding: AdapterRejectProviderOptionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}