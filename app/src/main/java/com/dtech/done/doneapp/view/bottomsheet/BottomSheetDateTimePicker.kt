package com.dtech.done.doneapp.view.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RelativeLayout
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.FragmentDateTimeBottomSheetBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.utilities.utils.DateUtil
import com.dtech.done.doneapp.view.base.ActivityBase
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class BottomSheetDateTimePicker constructor(private var selectedDate: String, private var startTime : String) : BottomSheetDialogFragment() {

    private lateinit var mListener: ISelectDobListener
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var selectedDateMeeting = ""
    private var selectedStartTimeMeeting = ""


    @SuppressLint("NewApi", "RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_date_time_bottom_sheet, null)
        val binding : FragmentDateTimeBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(ActivityBase.activity), R.layout. fragment_date_time_bottom_sheet, null, false);
        dialog.setContentView(binding.root)
        (binding.root.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))
        val datePicker: DatePicker = dialog.findViewById(R.id.date_picker)
        val timePicker: TimePicker = dialog.findViewById(R.id.timePicker)
        binding.isDateSelected = false
        binding.isStartTimeSelected = false
        datePicker.minDate = System.currentTimeMillis()

        val c: Calendar = Calendar.getInstance()
        if (!TextUtils.isEmpty(selectedDate)) {
            binding.isDateSelected = true
            binding.tvSelectedDate.text = DateUtil.convertSingleDateTimePicker(selectedDate)
            binding.rlDate.visibility = View.GONE
            binding.rlStartTime.visibility = View.VISIBLE
            if (selectedDate.contains("T")){
                selectedDate = selectedDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            }
            selectedDateMeeting = selectedDate
            val splitDate = selectedDate.split("-")
            mYear = Integer.parseInt(splitDate[0])
            mMonth = Integer.parseInt(splitDate[1]) - 1
            mDay = Integer.parseInt(splitDate[2])
        } else {
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
        }

        if (!TextUtils.isEmpty(startTime)){
            selectedStartTimeMeeting = startTime
            binding.isStartTimeSelected = true
            binding.tvSelectedStartTime.text = DateUtil.convertSingleTimePicker(selectedStartTimeMeeting)
            binding.rlStartTime.visibility = View.GONE

            var date = DateUtil.stringToDate(startTime)
            timePicker.hour = date!!.hours
            timePicker.minute = date!!.minutes
        }


        datePicker.updateDate(mYear, mMonth, mDay)

        dialog.findViewById<Button>(R.id.btnSelect).setOnClickListener {
            when {
                binding.rlDate.visibility == View.VISIBLE -> {
                    selectedDateMeeting =  "" + datePicker.year + "-" + (if((datePicker.month + 1) >= 10) (datePicker.month + 1)  else "0"+(datePicker.month + 1) )  + "-" + (if (datePicker.dayOfMonth >= 10) datePicker.dayOfMonth else "0"+datePicker.dayOfMonth)
                    binding.tvSelectedDate.text = DateUtil.convertSingleDateTimePicker("$selectedDateMeeting'T':00:00:00")
                    binding.rlDate.visibility = View.GONE
                    binding.rlStartTime.visibility = View.VISIBLE
                    binding.isDateSelected = true
                }
                binding.rlStartTime.visibility == View.VISIBLE -> {
                    selectedStartTimeMeeting = selectedDateMeeting+ "T"+(if((timePicker.hour) >= 10) (timePicker.hour)  else "0"+(timePicker.hour))+ ":"+(if((timePicker.minute) >= 10) (timePicker.minute)  else "0"+(timePicker.minute))+":"+"00"

                    if (DateUtil.getMinutesDifference( DateUtil.getCurrentDate()!! , selectedStartTimeMeeting ) > 1){
                        binding.tvSelectedStartTime.text = DateUtil.convertSingleTimePicker(selectedStartTimeMeeting)
                        binding.rlStartTime.visibility = View.GONE
                        binding.isStartTimeSelected = true
                    }else {
                        ActivityBase.activity.showToastMessage("Can't select previous time")
                    }

                }
                else -> {
                    mListener.onSelectDateAndTime(selectedDateMeeting/*+"T:00:00:00"*/ , selectedStartTimeMeeting)
                    dialog.dismiss()
                }
            }

        }
        dialog.findViewById<RelativeLayout>(R.id.rlSelectedMeetingDate).setOnClickListener {
            binding.rlDate.visibility = View.VISIBLE
            binding.isDateSelected = false
            binding.isStartTimeSelected = false
            binding.rlStartTime.visibility = View.GONE
        }
        dialog.findViewById<RelativeLayout>(R.id.rlSelectedStartTime).setOnClickListener {
            binding.rlStartTime.visibility = View.VISIBLE
            binding.isStartTimeSelected = false
        }
    }


    fun setMyListener(listener: ISelectDobListener) {
        mListener = listener
    }

    interface ISelectDobListener {
        fun onSelectDateAndTime(date: String , startTime : String )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.isDraggable = false
        return dialog
    }
}