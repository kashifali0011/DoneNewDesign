package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.Slots
import com.dtech.done.doneapp.databinding.AdapterSubServicesBinding
import com.dtech.done.doneapp.databinding.AdapterTimeSlotGridViewBinding

class TimeSlotGridViewAdapter(val context : Context, var slotsList: ArrayList<Slots> ,var mListener: OnItemClickListener) : RecyclerView.Adapter<TimeSlotGridViewAdapter.ViewHolder>() {
    lateinit var binding:AdapterTimeSlotGridViewBinding
    var selectPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_time_slot_grid_view ,parent, false)
        return ViewHolder(binding =  binding)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvTime.text = slotsList[position].slotTimeTo +" "+"to" +" "+ slotsList[position].slotTimeFrom
        if (selectPosition == -1){
            slotsList[position].isCheck = true
            selectPosition = position
        }

        if (slotsList[position].isCheck){
            viewHolder.binding.cvTime.setBackgroundDrawable(context.getDrawable(R.drawable.btn_rount_corner))
            viewHolder.binding.tvTime.setTextColor(Color.WHITE)
            mListener.onClick(position,slotsList[position].slotTimeTo.toString() , slotsList[position].slotTimeFrom.toString())
        }else{
            viewHolder.binding.cvTime.setBackgroundDrawable(context.getDrawable(R.drawable.btn_white_rount_corner))
            viewHolder.binding.tvTime.setTextColor(Color.parseColor("#474747"))
        }
        viewHolder.binding.clRoot.setOnClickListener {
            if (position != selectPosition) {
                slotsList[position].isCheck = true
                slotsList[selectPosition].isCheck = false
                notifyItemChanged(position)
                notifyItemChanged(selectPosition)
                selectPosition = position
            }
        }
    }
    override fun getItemCount(): Int {
        return slotsList.size
    }
    interface OnItemClickListener{
        fun onClick(position: Int , slotDateTo: String , slotDateFrom: String)
    }
    class ViewHolder(var binding: AdapterTimeSlotGridViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}