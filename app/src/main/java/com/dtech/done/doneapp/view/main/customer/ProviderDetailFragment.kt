package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderDetailRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.Slots
import com.dtech.done.doneapp.databinding.FragmentProviderDetailBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.BottomSheetDatePickerFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.TimeSlotGridViewAdapter
import com.dtech.done.doneapp.viewmodel.ProviderViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProviderDetailFragment : BaseFragment(), View.OnClickListener,BottomSheetDatePickerFragment.ISelectDobListener , TimeSlotGridViewAdapter.OnItemClickListener{
    lateinit var binding: FragmentProviderDetailBinding
    private lateinit var providerViewModel: ProviderViewModel
    var timeSlotGridViewAdapter: TimeSlotGridViewAdapter? = null
    var timeSlot: ArrayList<Slots> = ArrayList()
    var date = ""

    companion object {
        lateinit var instance: ProviderDetailFragment
        var service_id: Int = 0
        var provider_id: Int = 0
        var selectDate: String = ""
        var selectTime: String = ""
        var description: String = ""
        var rating = ""

        fun newInstance( service_id: Int, provider_id: Int, selectDate: String , selectTime: String, rating:String, description: String): ProviderDetailFragment {
            instance = ProviderDetailFragment()
            this.service_id = service_id
            this.provider_id = provider_id
            this.selectDate = selectDate
            this.selectTime = selectTime
            this.description = description
            this.rating = rating
            return instance
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_provider_detail, container, false)
        providerViewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)
        setUiObserver()
        getProvider()
        getDate()
        setListener()
        setToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimeSlotGridView()
    }
    private fun setUiObserver(){

        binding.tvRating.text = rating
        binding.tvDetail.text = description

        providerViewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })
        providerViewModel.getProviderDetails.observe(viewLifecycleOwner , Observer { response ->
            response?.let {
                if (it.slots.isNotEmpty()) {
                    timeSlot = it.slots
                    setTimeSlotGridView()
                }else{
                   binding.rvTimeSlots.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                }
                }
        })
    }

    private fun setListener() {
        binding.ivEdit.setOnClickListener(this)
        binding.btnReserveSlot.setOnClickListener(this)
        binding.llDate.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivEdit -> {
                addFragment(
                    R.id.mainContainer,
                    SelectServicesFragment(),
                    "SelectServicesFragment"
                )
            }
            R.id.btnReserveSlot -> {
               /* binding.btnReserveSlot.setBackgroundDrawable(
                    ActivityBase.activity.getDrawable(R.drawable.btn_rount_corner)
                )*/
                moveToMyCartFragment()
            }
            R.id.llDate ->{
                selectDates()
            }
        }
    }

    private fun setTimeSlotGridView() {
        val numberOfColumns = 3
        binding.rvTimeSlots.setLayoutManager(GridLayoutManager(requireActivity(), numberOfColumns))
        timeSlotGridViewAdapter = TimeSlotGridViewAdapter(requireActivity(),timeSlot, this)
        binding.rvTimeSlots.adapter = timeSlotGridViewAdapter
        }

    private fun getProvider(){
        providerViewModel.getProviderDetails(GetProviderDetailRequestModel("",service_id , provider_id ))
    }

    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "ABC Cleansers", null, ""))
    }
    private fun getDate(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = dateFormat.format(Date())
        date = currentDate

        var dateTime: String
        var calendar: Calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        dateTime = simpleDateFormat.format(calendar.time).toString()
        binding.tvDate.text = dateTime

    }
    private fun selectDates(){
        var bottomSheetDatePickerFragment = BottomSheetDatePickerFragment("","")
        bottomSheetDatePickerFragment.setMyListener(this)
        if (!bottomSheetDatePickerFragment.isAdded){
            bottomSheetDatePickerFragment.show(ActivityBase.activity.supportFragmentManager,"")
        }
    }

    override fun onSelectDob(dob: String) {
        date = dob
        val simpleDateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        val simpleDateFormat2 = SimpleDateFormat("dd-MMMM-yyyy")
        binding.tvDate.text = simpleDateFormat2.format(simpleDateFormat1.parse(dob)).toString()
    }

    override fun onDateCancel() {

    }

    override fun onClick(position: Int , slotDateTo: String , slotDateFrom: String) {
        Log.d("selectDate" , "$slotDateTo->$slotDateFrom")
    }
    private fun moveToMyCartFragment(){
        addFragment(R.id.mainContainer , MyCartFragment(), "MyCartFragment")
    }


}
