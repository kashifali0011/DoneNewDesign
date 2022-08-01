package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.Categories
import com.dtech.done.doneapp.data.model.responsemodel.ServiceCategories
import com.dtech.done.doneapp.databinding.AdapterQuestionAnswerBinding
import com.dtech.done.doneapp.databinding.SearchItemListBinding

class SearchItemsListAdapter(val context: Context,private var serviceCategoriesList:ArrayList<Categories>,private var mListener: OnItemClickListener?): RecyclerView.Adapter<SearchItemsListAdapter.ViewHolder>()  {
    lateinit var binding: SearchItemListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_item_list ,parent, false)
        return ViewHolder(binding = binding)
         }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvOptions.text = serviceCategoriesList[position].categoryDetail
       viewHolder.binding.tvOptions.setOnClickListener(View.OnClickListener {
           mListener!!.onItemClick(position)
       })
    }
    override fun getItemCount(): Int {
        return serviceCategoriesList.size
    }
    class ViewHolder(var binding: SearchItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
         fun onItemClick(position: Int)
    }
}