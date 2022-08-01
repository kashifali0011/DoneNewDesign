package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveCouponModel
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderData
import com.dtech.done.doneapp.data.model.responsemodel.Providers
import com.dtech.done.doneapp.databinding.AdapterPendingOrderListBinding
import com.dtech.done.doneapp.databinding.AdapterProvidersListBinding
import com.dtech.done.doneapp.utilities.extensions.load

class ProviderListAdapter(val context: Context, var providerList: ArrayList<Providers>, private var mListener: OnItemClickListener?): RecyclerView.Adapter<ProviderListAdapter.ViewHolder>() {

    lateinit var binding:AdapterProvidersListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_providers_list ,parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        providerList[position].providerDetail?.logoImage.let {viewHolder.binding.ivProviderIcon.load(it.toString())  }
        viewHolder.binding.tvProviderName.text = providerList[position].providerDetail?.name
       /* viewHolder.binding.tvPhoneNumber.text = providerList[position].providerDetail!!.phone*/
        viewHolder.binding.tvDescription.text = providerList[position].providerDetail!!.description
        viewHolder.binding.tvRating.text = providerList[position].rating
        viewHolder.binding.tvPrice.text = "SAR"+" "+ providerList[position].servicePrice.toString()
        viewHolder.binding.tvCRNNumber.text = providerList[position].providerDetail!!.crNumber
        viewHolder.binding.cvProviderDetail.setOnClickListener(View.OnClickListener {
            mListener!!.onItemClick(position, providerList[position].serviceId!!.toInt() , providerList[position].providerId!!.toInt() ,providerList[position].rating.toString() , providerList[position].providerDetail!!.description.toString())
        })
    }

    override fun getItemCount(): Int {
        return providerList.size
    }
    class ViewHolder(var binding: AdapterProvidersListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(position: Int,service_id: Int ,provider_id: Int ,rating: String, description: String)
    }

}