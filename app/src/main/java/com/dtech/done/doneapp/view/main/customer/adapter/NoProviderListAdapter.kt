package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterNoProviderListBinding

class NoProviderListAdapter(val context : Context, var mListener: OnClickListener?) : RecyclerView.Adapter<NoProviderListAdapter.ViewHolder>() {
    lateinit var binding:AdapterNoProviderListBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding  = AdapterNoProviderListBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_no_provider_list, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.cvRejectBtn.setOnClickListener(View.OnClickListener {
            mListener!!.reject()
        })
        viewHolder.binding.cvAcceptBtn.setOnClickListener(View.OnClickListener {
            mListener!!.accept()
        })
    }

    override fun getItemCount(): Int {
       return 2
    }
    class ViewHolder(var binding: AdapterNoProviderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
      //  fun onItemClick(position: Int)
        fun reject()
        fun accept()
    }
}