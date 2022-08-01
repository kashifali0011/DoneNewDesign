package com.dtech.done.doneapp.view.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import com.dtech.done.doneapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import java.util.*

/**
 * Develop By Messagemuse
 */

@SuppressLint("ValidFragment")
class BottomSheetDatePickerFragment constructor(private var selectedDate: String, private var type: String) : BottomSheetDialogFragment() {

    private lateinit var mListener: ISelectDobListener
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0


    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_bottom_sheet_date_picker, null)
        dialog.setContentView(contentView)

        val datePicker: DatePicker = dialog.findViewById(R.id.date_picker)
        datePicker.minDate = (System.currentTimeMillis())


        val c: Calendar = Calendar.getInstance()
        if (!TextUtils.isEmpty(selectedDate)) {
            if (selectedDate.contains("T")) {
                selectedDate = selectedDate.split("T")[0]
            }
            val splitDate = selectedDate.split("-")
            mYear = Integer.parseInt(splitDate[0])
            mMonth = Integer.parseInt(splitDate[1]) - 1
            mDay = Integer.parseInt(splitDate[2])
        } else {
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
        }
        (contentView.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))
        datePicker.updateDate(mYear, mMonth, mDay)
        dialog.findViewById<Button>(R.id.btnSelect).setOnClickListener {

            mListener.onSelectDob("" + datePicker.year + "-" + (if ((datePicker.month + 1) >= 10) (datePicker.month + 1) else "0" + (datePicker.month + 1)) + "-" + (if (datePicker.dayOfMonth >= 10) datePicker.dayOfMonth else "0" + datePicker.dayOfMonth))
            dialog.dismiss()
        }
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        mListener.onDateCancel()
    }

    fun setMyListener(listener: ISelectDobListener) {
        mListener = listener
    }

    interface ISelectDobListener {
        fun onSelectDob(dob: String)
        fun onDateCancel()
    }


}