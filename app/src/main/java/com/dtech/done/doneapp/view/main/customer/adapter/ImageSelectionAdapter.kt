package com.dtech.done.doneapp.view.main.customer.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.AdapterImageSelectionBinding

class ImageSelectionAdapter(val context:Context, val arrayList :ArrayList<Uri>, private var mListener: OnItemClickListener): RecyclerView.Adapter<ImageSelectionAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterImageSelectionBinding = AdapterImageSelectionBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.adapter_image_selection, parent, false))
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.ivCancel
       if (position < (arrayList.size)) {
           Glide.with(context).load(arrayList[position]).into(viewHolder.binding.ivImage)
           viewHolder.binding.ivCancel.visibility = View.VISIBLE
       }else {
           viewHolder.binding.ivCancel.visibility = View.GONE
       }
       viewHolder.binding.ivCancel.setOnClickListener(View.OnClickListener {
            mListener!!.onDeleteImage(position)
        })
        viewHolder.binding.ivAddIcon.setOnClickListener(View.OnClickListener {
            mListener!!.addFile(position)
        })
    }

    override fun getItemCount(): Int {
        return 5
    }

    class ViewHolder(var binding: AdapterImageSelectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onDeleteImage(position: Int)
        fun addFile(position: Int)
    }

}