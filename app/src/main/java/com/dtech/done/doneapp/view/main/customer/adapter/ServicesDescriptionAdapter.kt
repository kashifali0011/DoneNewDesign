package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterServicesDiscriptionBinding

class ServicesDescriptionAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var binding : AdapterServicesDiscriptionBinding = AdapterServicesDiscriptionBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_services_discription,parent,false))
        return ViewHolder(binding = binding)

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as ViewHolder

    }

    override fun getItemCount(): Int = 8

    class ViewHolder(var binding: AdapterServicesDiscriptionBinding) :
                    RecyclerView.ViewHolder(binding.root)




}