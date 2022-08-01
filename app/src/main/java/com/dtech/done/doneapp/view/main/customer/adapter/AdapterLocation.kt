package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.databinding.AdapterLocationBinding

class AdapterLocation(val context: Context, val mList :ArrayList<GetCustomerAddressesResponseModel>, private var mListener: OnItemClickListener): RecyclerView.Adapter<AdapterLocation.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterLocationBinding = AdapterLocationBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_location, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.model = mList[position]
        viewHolder.binding.tvAddress.movementMethod = ScrollingMovementMethod()
        viewHolder.binding.rlMain.setOnClickListener{

            var indexedValue = mList.indexOfFirst { it.isSelect }
            if (indexedValue >= 0){
                mList[indexedValue].isSelect = false
                notifyItemChanged(indexedValue)
            }

            mList[position].isSelect = true
            notifyItemChanged(position)
            mListener.onClickMain(position)
        }
        viewHolder.binding.ivDelete.setOnClickListener {
            mListener.onDelete(position)
        }

        viewHolder.binding.ivEdit.setOnClickListener {
            mListener.onEdit(position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getList() : ArrayList<GetCustomerAddressesResponseModel>{
        return mList
    }

    class ViewHolder(var binding: AdapterLocationBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onClickMain(position: Int)
        fun onDelete(position: Int)
        fun onEdit(position: Int)
    }

}