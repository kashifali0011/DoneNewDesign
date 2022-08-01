package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterBannerSliderBinding

class BannerSliderAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(AdapterBannerSliderBinding.bind(LayoutInflater.from(context).inflate(R.layout.adapter_banner_slider, parent, false)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 8

    class ViewHolder(var binding: AdapterBannerSliderBinding) :
        RecyclerView.ViewHolder(binding.root)
}