package com.dtech.done.doneapp.view.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.AccountTopUpFragment
import com.dtech.done.doneapp.view.main.customer.SelectServicesFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TopUpBottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseBottomSheetTransparentDialog)
    }
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_top_up_bottom_sheet, null)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(ActivityBase.activity, android.R.color.transparent))

        val bottomSheet: FrameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val ivCross = dialog.findViewById<ImageView>(R.id.ivClose)
        ivCross.setOnClickListener {
            dialog.dismiss()
        }

        val btnSaveAndContinue = dialog.findViewById<Button>(R.id.btnSaveAndContinue)
        btnSaveAndContinue.setOnClickListener {
            dialog.dismiss()
            (ActivityBase.activity as MainActivity).addFragment(R.id.mainContainer , AccountTopUpFragment(),"AccountTopUpFragment")
        }
    }



}