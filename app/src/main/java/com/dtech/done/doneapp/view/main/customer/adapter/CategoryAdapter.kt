package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SetPositionCategoryModel
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.data.model.responsemodel.MainServices
import com.dtech.done.doneapp.databinding.ActivityAuthBinding.inflate
import com.dtech.done.doneapp.databinding.AdapterCategoryBinding
import com.dtech.done.doneapp.utilities.extensions.load

class CategoryAdapter(var context: Context, private val mServicesList: ArrayList<MainServices> = ArrayList(),
    private var mListener: CategoryAdapter.OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
      var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding: AdapterCategoryBinding = AdapterCategoryBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_category, parent, false))

        return ViewHolder(binding = binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        mServicesList[position].serviceIcon?.let { viewHolder.binding.ivServiceIcon.load(it) }
        mServicesList[position].serviceTitle?.let { viewHolder.binding.tvServiceType.text = it }
        viewHolder.binding.rbSelectServices.isChecked = mServicesList[position].isCheck
        viewHolder.binding.rlSearchLocation.setOnClickListener{
            var index = mServicesList.indexOfFirst { it.isCheck }
            mServicesList.map { it.isCheck = false }
            mServicesList[position].isCheck = true
            if (index >= 0){
                notifyItemChanged(index)
                notifyItemChanged(position)
            }
            mListener.onSelectedItem(position)
        }
    }
    override fun getItemCount(): Int = mServicesList!!.size

    class ViewHolder(var binding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onSelectedItem(position: Int)
    }
}