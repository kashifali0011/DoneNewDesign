package com.dtech.done.doneapp.view.main.customer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.MainServices
import com.dtech.done.doneapp.data.model.responsemodel.SubServices
import com.dtech.done.doneapp.databinding.AdapterCategoryBinding
import com.dtech.done.doneapp.databinding.AdapterServicesBinding
import com.dtech.done.doneapp.utilities.extensions.load

class ServicesAdapter(
    var context: Context,
    private val subServicesList: ArrayList<SubServices> = ArrayList()
    , private var mListener: OnItemClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var servicesDescriptionAdapter : ServicesDescriptionAdapter? = null
    var selectOption :Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var binding: AdapterServicesBinding = AdapterServicesBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_services, parent, false))
        return ViewHolder(binding = binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as ViewHolder
        subServicesList[position].serviceIcon?.let { viewHolder.binding.ivSubServiceIcon.load(it) }
        subServicesList[position].serviceTitle?.let { viewHolder.binding.tvServiceName.text = it }

        viewHolder.binding.cvCategory.setOnClickListener {
               mListener!!.onSubServiceItemClickListener(position)

        }
    }

    override fun getItemCount(): Int = subServicesList.size

    class ViewHolder(var binding: AdapterServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onSubServiceItemClickListener(position: Int)
    }
}