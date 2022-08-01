package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveCouponModel
import com.dtech.done.doneapp.databinding.AdapterCouponBinding

class CouponAdapter(var context: Context, var arrayList: ArrayList<SaveCouponModel>, private var mListener: OnItemClickListener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding: AdapterCouponBinding = AdapterCouponBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_coupon, parent, false))

        return ViewHolder(binding = binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.binding.tvCouponName.setText(arrayList[position].coupon)

        viewHolder.binding.cvRemoveCoupon.setOnClickListener {
            mListener!!.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = arrayList.size

    class ViewHolder(var binding: AdapterCouponBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}