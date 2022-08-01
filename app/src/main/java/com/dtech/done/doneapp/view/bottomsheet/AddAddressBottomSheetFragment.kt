package com.dtech.done.doneapp.view.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.utilities.enums.LocationLabel
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.SelectServicesFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddAddressBottomSheetFragment(
    var lat: Double, var long: Double,
    var address: String, var label: Int,
    var labelName: String,
    var street: String?, var floor: String?, var notes: String?,
    var isEdit: Boolean, var mListener: ISelectLocation): BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseBottomSheetTransparentDialog)
    }
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_add_address_bottom, null)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(ActivityBase.activity, android.R.color.transparent))

        val bottomSheet: FrameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val ivCross = dialog.findViewById<ImageView>(R.id.ivClose)
        val llOther = dialog.findViewById<LinearLayout>(R.id.llOther)
        val etOther = dialog.findViewById<EditText>(R.id.etOther)
        val llHome = dialog.findViewById<LinearLayout>(R.id.llHome)
        val llWork = dialog.findViewById<LinearLayout>(R.id.llWork)
        val rlHome = dialog.findViewById<RelativeLayout>(R.id.rlHome)
        val rlWork = dialog.findViewById<RelativeLayout>(R.id.rlWork)
        val rlOther = dialog.findViewById<RelativeLayout>(R.id.rlAdd)
        val ivHome = dialog.findViewById<ImageView>(R.id.ivHome)
        val ivWork = dialog.findViewById<ImageView>(R.id.ivWork)
        val ivOther = dialog.findViewById<ImageView>(R.id.ivOther)
        val etStreet = dialog.findViewById<EditText>(R.id.etStreet)
        val etFloor = dialog.findViewById<EditText>(R.id.etFloor)
        val etNotes = dialog.findViewById<EditText>(R.id.etNote)
        val tvAddress = dialog.findViewById<TextView>(R.id.tvAddress)
        val tvAddNewAddress = dialog.findViewById<TextView>(R.id.tvAddNewAddress)
        val tvOther = dialog.findViewById<TextView>(R.id.tvOther)
        val btnSaveAndContinue = dialog.findViewById<Button>(R.id.btnSaveAndContinue)

        tvAddress.text = address
        etNotes.setText(notes)
        etFloor.setText(floor)
        etStreet.setText(street)
        if (isEdit){
            tvAddNewAddress.text = getString(R.string.update_new_address)
        }else{
            tvAddNewAddress.text = getString(R.string.add_a_new_address)
        }



        llOther.setOnClickListener {
            label = LocationLabel.OTHER.value
            etOther.visibility = View.VISIBLE
            rlOther.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            rlHome.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            rlWork.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            ivOther.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            ivHome.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivWork.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            if (etOther.text.toString().isNullOrEmpty()){
                btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
            }else {
                btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            }
        }

        llHome.setOnClickListener {
            label = LocationLabel.HOME.value
            etOther.visibility = View.GONE
            rlOther.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            rlHome.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            rlWork.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivOther.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivHome.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            ivWork.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        }

        llWork.setOnClickListener {
            label = LocationLabel.WORK.value
            etOther.visibility = View.GONE
            rlOther.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            rlHome.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            rlWork.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivOther.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivHome.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            ivWork.imageTintList = ContextCompat.getColorStateList(requireActivity(),R.color.white)
        }


        when(label){
            LocationLabel.HOME.value -> {
                llHome.performClick()
            }
            LocationLabel.WORK.value -> {
                llWork.performClick()
            }

            LocationLabel.OTHER.value -> {
                etOther.setText(labelName)
                llOther.performClick()
            }
        }


        ivCross.setOnClickListener {
            dialog.dismiss()
        }




        etOther.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (etOther.text.toString().isNullOrEmpty()){
                    btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)
                }else {
                    btnSaveAndContinue.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
                }
            }
        })
        btnSaveAndContinue.setOnClickListener {
            var labelName  = ""
            when(label){
                LocationLabel.HOME.value -> {
                    labelName = "Home"
                }
                LocationLabel.WORK.value -> {
                    labelName = "Work"
                }
                LocationLabel.OTHER.value -> {
                    if (etOther.text.toString().isNullOrEmpty()){
                        ActivityBase.activity.showToastMessage(getString(R.string.other_label_empty))
                        return@setOnClickListener
                    }else {
                        labelName = etOther.text.toString()
                    }

                }
                else ->
                    requireActivity().showToastMessage(getString(R.string.please_select_label))

            }
            mListener.onLocationSelect(lat , long , address , labelName , etStreet.text.toString() , etFloor.text.toString() , etNotes.text.toString())
            requireActivity().showToastMessage(getString(R.string.successfully_save_address))
            dialog.dismiss()
           /* (ActivityBase.activity as MainActivity).addFragment(R.id.mainContainer , SelectServicesFragment(),"")*/
        }
    }


    interface ISelectLocation {
        fun onLocationSelect(lat : Double ,long : Double ,address : String , label : String , street : String , floor : String , notes : String)
    }

}