package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveLocationStatusModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.data.model.responsemodel.LoginUserResponseModel
import com.dtech.done.doneapp.databinding.FragmentHomeCustomerBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.AdapterLocation
import com.dtech.done.doneapp.viewmodel.CustomerViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class HomeFragmentCustomer : BaseFragment(), View.OnClickListener, AdapterLocation.OnItemClickListener {

    lateinit var binding: FragmentHomeCustomerBinding
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: CustomerViewModel
    var adapterLocation : AdapterLocation? = null

    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

    }

    var saveLocationStatusList: ArrayList<SaveLocationStatusModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_customer, container, false)
        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        setUiObserver()
        setToolbar()
        setLocationAdapter(arrayListOf())
        setListener()
        return binding.root
    }

    private fun setUiObserver() {
        viewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })

        viewModel.getCustomerResponse.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                binding.llNoLocation.visibility = View.GONE
                if (it.size < 5) {
                    binding.tvAddNewLocation.visibility = View.VISIBLE
                    binding.tvYouAddMaxLoc.visibility = View.VISIBLE
                }
                else {
                    binding.tvAddNewLocation.visibility = View.GONE
                    binding.tvYouAddMaxLoc.visibility = View.GONE

                }
                    setLocationAdapter(it)

            }else {
                binding.llNoLocation.visibility = View.VISIBLE
                binding.tvYouAddMaxLoc.visibility = View.GONE

                //binding.tvAddNewLocation.visibility = View.GONE
                setLocationAdapter(arrayListOf())
            }
        })

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                callGetAddress()
            }
        })

    }

    private fun setListener() {
        binding.tvViewOnMap.setOnClickListener(this)
        binding.tvAddNewLocation.setOnClickListener(this)
        binding.rlSearchLocation.setOnClickListener(this)
        binding.llNoLocation.setOnClickListener(this)
        binding.btnProceedWithService.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    fun setToolbar() {
        callGetAddress()
        if (DoneApp.db.getString(Constants.CONST_USER_TYPE)!!.toInt() == UserType.GUEST.value)
            (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(isNavigationShown = true, isBackShown = false, placesShown = false, notificationShown = false, cartShown = true, userName = "Guest", title = "", subTitle = null, placeName = ""))
        else {
            var user = Gson().fromJson<LoginUserResponseModel>(DoneApp.db.getString(Constants.CONST_USER_DATA), LoginUserResponseModel::class.java)
            (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(isNavigationShown = true, isBackShown = false, placesShown = false, notificationShown = true, cartShown = true, userName = user.name!!, title = "", subTitle = null, placeName = ""))

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvViewOnMap -> {
                if (adapterLocation!!.getList().size < 5)
                    addFragment(R.id.mainContainer,  SelectLocationFragment("",0.0,0.0,"","","","",false), "MyCartFragment")
            }
            R.id.tvAddNewLocation -> {
                if (adapterLocation!!.getList().size < 5)
                    addFragment(R.id.mainContainer,  SelectLocationFragment("",0.0,0.0,"","","","",false), "MyCartFragment")
            }
            R.id.rlSearchLocation -> {
                if (adapterLocation!!.getList().size < 5)
                    addFragment(R.id.mainContainer, SelectLocationFragment("",0.0,0.0,"","","","",false), "SelectLocationFragment")
            }

            R.id.llNoLocation -> {
                saveLocationStatusList.add(SaveLocationStatusModel(1))
                addFragment(R.id.mainContainer,  SelectLocationFragment("",0.0,0.0,"","","","",false), "SelectLocationFragment")
            }
            R.id.btnProceedWithService -> {
                addFragment(R.id.mainContainer , SelectServicesFragment(),"")
            }
        }
    }

    private fun callGetAddress() {
        viewModel.getCustomerAddresses()
    }

    private fun setLocationAdapter(data : ArrayList<GetCustomerAddressesResponseModel>){
        binding.rvSaveLocations.layoutManager = LinearLayoutManager(requireActivity())
        adapterLocation = AdapterLocation(requireActivity() , data , this)
        binding.rvSaveLocations.adapter = adapterLocation
    }

    override fun onClickMain(position: Int) {
        binding.btnProceedWithService.isEnabled = true
        binding.btnProceedWithService.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
        mMap.clear()
        val sydney = LatLng(adapterLocation!!.getList()[position].latitude!!.toDouble(), adapterLocation!!.getList()[position].longitude!!.toDouble())
        mMap.addMarker(MarkerOptions().position(sydney).title(adapterLocation!!.getList()[position].addressName))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f), 1000, null)
    }

    override fun onDelete(position: Int) {
        ActivityBase.activity.showToastMessage(getString(R.string.successfully_deleted))
        viewModel.deleteAddress(adapterLocation!!.getList()[position].addressId , if (adapterLocation!!.getList()[position].addressIdLocal != null) adapterLocation!!.getList()[position].addressIdLocal else "")
    }

    override fun onEdit(position: Int) {
        when(DoneApp.db.getString(Constants.CONST_USER_TYPE)) {
            UserType.GUEST.value.toString() -> {
                addFragment(R.id.mainContainer , SelectLocationFragment(adapterLocation!!.getList()[position].addressIdLocal ,adapterLocation!!.getList()[position].latitude!!.toDouble() ,adapterLocation!!.getList()[position].longitude!!.toDouble() ,adapterLocation!!.getList()[position].addressName!! ,adapterLocation!!.getList()[position].street!!,adapterLocation!!.getList()[position].floor!!,adapterLocation!!.getList()[position].providerNote!!,true) , "SelectLocationFragment")

            }
            UserType.CUSTOMER.value.toString() -> {
                addFragment(R.id.mainContainer , SelectLocationFragment(adapterLocation!!.getList()[position].addressId.toString() ,adapterLocation!!.getList()[position].latitude!!.toDouble() ,adapterLocation!!.getList()[position].longitude!!.toDouble() ,adapterLocation!!.getList()[position].addressName!! ,adapterLocation!!.getList()[position].street,adapterLocation!!.getList()[position].floor,adapterLocation!!.getList()[position].providerNote,true) , "SelectLocationFragment")

            }
        }

    }

    override fun onResume() {
        super.onResume()
        setUiObserver()
    }
}