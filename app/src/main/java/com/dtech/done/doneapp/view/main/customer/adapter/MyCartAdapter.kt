package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterMyCartBinding


class MyCartAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding: AdapterMyCartBinding = AdapterMyCartBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_my_cart, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }
     class ViewHolder(var binding: AdapterMyCartBinding) :
            RecyclerView.ViewHolder(binding.root)

}