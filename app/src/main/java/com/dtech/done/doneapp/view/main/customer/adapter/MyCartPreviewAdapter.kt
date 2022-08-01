package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterMyCartPreviewBinding


class MyCartPreviewAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding: AdapterMyCartPreviewBinding = AdapterMyCartPreviewBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_my_cart_preview, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as ViewHolder
        viewHolder.binding.tvDiscountPrice.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG


    }

    override fun getItemCount(): Int {
        return 5
    }
     class ViewHolder(var binding: AdapterMyCartPreviewBinding) :
            RecyclerView.ViewHolder(binding.root)

}