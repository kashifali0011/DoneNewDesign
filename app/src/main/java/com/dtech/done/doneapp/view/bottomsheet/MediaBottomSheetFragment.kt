package com.dtech.done.doneapp.view.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.bottomsheet.adapter.AdapterMoreBottom
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Develop By Messagemuse
 */

class MediaBottomSheetFragment constructor(val mList: ArrayList<String>, private val cancelText : String) : BottomSheetDialogFragment()
   {
    private lateinit var mListener: ISelectListener
    var adapter : AdapterMoreBottom? = null

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setStyle(STYLE_NO_TITLE, R.style.BaseBottomSheetTransparentDialog)
       }
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_more_bottom_sheet, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(ActivityBase.activity,android.R.color.transparent))
        if (cancelText.isNotEmpty()){
            dialog.findViewById<RelativeLayout>(R.id.rlCancel).visibility = View.VISIBLE
            dialog.findViewById<TextView>(R.id.tvCancel).text = cancelText

        }else{
            dialog.findViewById<RelativeLayout>(R.id.rlCancel).visibility = View.GONE
        }
        val rvMore = dialog.findViewById<RecyclerView>(R.id.rvMore)
        val linearLayoutManager = LinearLayoutManager(ActivityBase.activity)
        rvMore.layoutManager = linearLayoutManager
        adapter = AdapterMoreBottom(ActivityBase.activity , mList)
        adapter!!.setMyListener(object : AdapterMoreBottom.IMoreClickListener{
            override fun onClickItem(position: Int) {
                mListener.onMediaSelect(position)
                dialog.dismiss()
            }
        })
        rvMore.adapter = adapter
        dialog.findViewById<RelativeLayout>(R.id.rlCancel).setOnClickListener {
            mListener.onMediaCancel()
            dialog.dismiss()
        }
    }

    fun setMyListener(listener: ISelectListener) {
        mListener = listener
    }

    interface ISelectListener {
        fun onMediaSelect(pos: Int)
        fun onMediaCancel()
    }
}