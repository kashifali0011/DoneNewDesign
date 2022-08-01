package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveWalletLayoutModel
import com.dtech.done.doneapp.data.model.custommodel.SetPositionCategoryModel
import com.dtech.done.doneapp.databinding.AdapterWalletBinding
import com.dtech.done.doneapp.databinding.AdapterWalletWithDateBinding

class WalletAdapter(var context: Context, var arrayList: ArrayList<SaveWalletLayoutModel>, var mListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                ViewHolder(AdapterWalletBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet, parent, false)))
            }
            1 -> {
                ViewHolderWithDate(AdapterWalletWithDateBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet_with_date, parent, false)))
            }
            else -> {
                ViewHolder(AdapterWalletBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet, parent, false)))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when(arrayList[position].checkStatus){
            0 ->{
                val viewHolder = holder as ViewHolder
               viewHolder.binding.clRoot.setOnClickListener {
                   mListener.onItemClick(position)
               } }
           1 ->{
               val viewHolderDate = holder as ViewHolderWithDate
               viewHolderDate.binding.clRootWithDate.setOnClickListener {
                   mListener.onItemClick(position)
               } }
            else ->{
                val viewHolder = holder as ViewHolder
                viewHolder.binding.clRoot.setOnClickListener {
                    mListener.onItemClick(position)
                } }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return arrayList[position].checkStatus
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(var binding: AdapterWalletBinding) :
        RecyclerView.ViewHolder(binding.root)


    class ViewHolderWithDate(var binding: AdapterWalletWithDateBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}