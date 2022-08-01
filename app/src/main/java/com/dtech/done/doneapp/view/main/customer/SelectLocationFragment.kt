package com.dtech.done.doneapp.view.main.customer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.databinding.FragmentSelectLocationBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.hideAppLoader
import com.dtech.done.doneapp.utilities.extensions.showAppLoader
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.utilities.helper.CalculateAddress
import com.dtech.done.doneapp.utilities.helper.Permissions
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.AddAddressBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.viewmodel.CustomerViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import com.dtech.done.doneapp.data.model.custommodel.SaveCouponApplyModel
import com.dtech.done.doneapp.data.model.custommodel.SaveLocationStatusModel
import com.dtech.done.doneapp.data.model.requestmodel.SaveAddressRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.UpdateAddressRequestModel
import com.dtech.done.doneapp.utilities.enums.LocationLabel

class SelectLocationFragment(
    var addressId: String,
    var lat: Double, var long: Double, var label:String,
    var street: String?, var floor: String?, var notes: String?,
    var isEdit: Boolean) : BaseFragment(),
    OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraIdleListener, MainActivity.IPlaceSetListener, View.OnClickListener, AddAddressBottomSheetFragment.ISelectLocation {

    lateinit var binding: FragmentSelectLocationBinding
    private lateinit var mMap: GoogleMap
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mCurrentLocation: Location? = null
    var REQUEST_CODE_MAP = 1009
    var address = ""
    private lateinit var viewModel: CustomerViewModel

    var saveLocationStatusList: ArrayList<SaveLocationStatusModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)
        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        setUiObserver()
        setToolbar()
        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.btnAddLocation.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun setUiObserver() {
        viewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            ActivityBase.activity.supportFragmentManager.popBackStackImmediate()
        })
    }


    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(isNavigationShown = false, isBackShown = true, placesShown = true, notificationShown = false, cartShown = false, userName = "Guest", title = "", subTitle = null, placeName = ""))
        (ActivityBase.activity as MainActivity).setPlaceOnMap(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ActivityBase.activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {                //Location Permission already granted
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(ActivityBase.activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_MAP)
                return
            }
        } else {
            buildGoogleApiClient()
        }
    }

    @SuppressLint("MissingPermission")
    private fun buildGoogleApiClient() {
        mMap!!.isMyLocationEnabled = true
        mGoogleApiClient = GoogleApiClient.Builder(ActivityBase.activity)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient!!.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_MAP -> {
                buildGoogleApiClient()
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        if (isEdit && mCurrentLocation != null){
            mCurrentLocation!!.latitude = lat
            mCurrentLocation!!.longitude = long
        }
        moveToCurrentLocation()
        mMap.setOnCameraIdleListener(this)
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun moveToCurrentLocation() {
        if (mCurrentLocation == null) {
            if (ActivityBase.activity != null) {
                if (ActivityCompat.checkSelfPermission(ActivityBase.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityBase.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Permissions.requestPermission(ActivityBase.activity)
                    return
                }
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient!!)

            if (mCurrentLocation != null) {
                if (isEdit){
                    mCurrentLocation!!.latitude = lat
                    mCurrentLocation!!.longitude = long
                }
                val loc = LatLng(mCurrentLocation!!.latitude, mCurrentLocation!!.longitude)
                if (mMap != null) {
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.5f))
                }
                setAddress(mCurrentLocation!!.latitude , mCurrentLocation!!.longitude)
            }
        } else {
            val loc = LatLng(mCurrentLocation!!.latitude, mCurrentLocation!!.longitude)
            if (mMap != null) {
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.5f))
            }
            setAddress(mCurrentLocation!!.latitude , mCurrentLocation!!.longitude)
        }

    }

    private fun moveToSelectPlace(){
        val loc = LatLng(mCurrentLocation!!.latitude, mCurrentLocation!!.longitude)
        if (mMap != null) {
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.5f))
        }
    }

    override fun onCameraIdle() {
        mCurrentLocation!!.latitude = mMap!!.cameraPosition.target.latitude
        mCurrentLocation!!.longitude = mMap!!.cameraPosition.target.longitude
        setAddress(mMap!!.cameraPosition.target.latitude, mMap!!.cameraPosition.target.longitude)
    }


    private fun setAddress(lat: Double, long: Double) {
        CalculateAddress(object : CalculateAddress.IAddressListener {
            override fun showLoading() {
                ActivityBase.activity.showAppLoader()
            }

            override fun endLoading() {
                hideAppLoader()
            }

            override fun addressCalculated(result: String?) {
                address = result!!
                ActivityBase.activity.runOnUiThread {
                    (ActivityBase.activity as MainActivity).setPlaceName(result!!)
                }
            }

        }).execute(lat, long)
    }

    override fun onPlaceSelect(lat: Double, long: Double) {
        mCurrentLocation!!.latitude = lat
        mCurrentLocation!!.longitude = long
        moveToSelectPlace()
    }

    private fun getLabelId(): Int {
        return if (label.isNullOrEmpty())
            -1
        else if (label.equals("Home")){
            LocationLabel.HOME.value
        }else if (label.equals("Work")){
            LocationLabel.WORK.value
        }else {
            LocationLabel.OTHER.value
        }
    }
    private fun showSaveAddress() {

       val bottomSheetListFragment = AddAddressBottomSheetFragment(mCurrentLocation!!.latitude  , mCurrentLocation!!.longitude , address ,getLabelId(),label,street,floor,notes ,isEdit, this)
        if (!bottomSheetListFragment.isAdded) {
            bottomSheetListFragment.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddLocation -> {
                showSaveAddress()
            }
        }
    }

    override fun onLocationSelect(lat: Double, long: Double, address: String, label: String, street: String, floor: String, notes: String) {
        if (isEdit){
            viewModel.updateAddress(UpdateAddressRequestModel(lat,long,label,address,"",street,floor,notes,addressId))
        }else {
            when (DoneApp.db.getString(Constants.CONST_USER_TYPE)) {
                UserType.GUEST.value.toString() -> {
                    var addressList: ArrayList<GetCustomerAddressesResponseModel>? = arrayListOf()
                    var customerListJson = DoneApp.db.getString(Constants.USER_LOCATION)
                    if (!customerListJson.isNullOrEmpty()) {
                        val itemType = object : TypeToken<ArrayList<GetCustomerAddressesResponseModel>>() {}.type
                        addressList = Gson().fromJson<ArrayList<GetCustomerAddressesResponseModel>>(customerListJson, itemType)
                    }
                    if (addressList == null) {
                        addressList = arrayListOf()
                    }
                    addressList.add(GetCustomerAddressesResponseModel(0, label, address, floor, lat.toString(), long.toString(), notes, street, false, UUID.randomUUID().toString()))
                    DoneApp.db.putString(Constants.USER_LOCATION, Gson().toJson(addressList))
                    ActivityBase.activity.supportFragmentManager.popBackStackImmediate()
                }

                UserType.CUSTOMER.value.toString() -> {
                    viewModel.saveAddress(SaveAddressRequestModel(lat, long, label, address, "", street, floor, notes))
                }
            }
        }
    }


}