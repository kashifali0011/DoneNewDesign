package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.persistableBundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.SubServices
import com.dtech.done.doneapp.databinding.AdapterSubServicesBinding
import com.dtech.done.doneapp.databinding.SearchItemListBinding

class SubServiceAdapter(val context: Context, var subServices: ArrayList<SubServices> = ArrayList()
): RecyclerView.Adapter<SubServiceAdapter.ViewHolder>() {
    lateinit var binding: AdapterSubServicesBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_sub_services ,parent, false)
        return ViewHolder(binding = binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Html.fromHtml(subServices[position].serviceDetail.toString(),Html.FROM_HTML_MODE_LEGACY).let { binding.tvOptions.text = it }
    }

    override fun getItemCount(): Int {
        return subServices.size
    }
    class ViewHolder(var binding: AdapterSubServicesBinding) :
        RecyclerView.ViewHolder(binding.root)
}