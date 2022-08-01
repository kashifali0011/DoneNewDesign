package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveNotificationModel
import com.dtech.done.doneapp.databinding.AdapterNotificationBinding
import com.dtech.done.doneapp.databinding.AdapterNotificationDateBinding

class NotificationAdapter(var context: Context,var arrayList: ArrayList<SaveNotificationModel>, var mListener: NotificationAdapter.OnItemClickListener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return when (viewType) {
                    0 -> {
                        ViewHolder(AdapterNotificationBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_notification, parent, false)))
                    }
                    1 -> {
                        ViewHolderWithDay(AdapterNotificationDateBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_notification_date, parent, false)))
                    }
                    else -> {
                        ViewHolder(AdapterNotificationBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_notification, parent, false)))
                    }
                }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(arrayList[position].checkStatus){
            0 -> {
                var viewHolder = holder as ViewHolder
                viewHolder.binding.tvNotificationDetails.setOnClickListener(View.OnClickListener {
                    mListener!!.onItemClick(position)
                })
            }

            1 -> {
                var viewHolder = holder as ViewHolderWithDay
                viewHolder.binding.tvNotificationDetails.setOnClickListener(View.OnClickListener {
                    mListener!!.onItemClick(position)
                })
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return arrayList[position].checkStatus
    }

    class ViewHolder(var binding: AdapterNotificationBinding) :
            RecyclerView.ViewHolder(binding.root)

    class ViewHolderWithDay(var binding: AdapterNotificationDateBinding) :
            RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}