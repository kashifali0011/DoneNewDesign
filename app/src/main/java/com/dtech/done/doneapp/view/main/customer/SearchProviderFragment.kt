package com.dtech.done.doneapp.view.main.customer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentSearchProviderBinding
import com.dtech.done.doneapp.utilities.helper.CalculateAddress
import com.dtech.done.doneapp.utilities.helper.Permissions
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class SearchProviderFragment : BaseFragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    lateinit var binding: FragmentSearchProviderBinding
    private lateinit var mMap: GoogleMap
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mCurrentLocation: Location? = null
    var REQUEST_CODE_MAP = 1009

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_provider, container, false)
        setToolbar()
        Handler(Looper.myLooper()!!).postDelayed({
            addFragment(R.id.mainContainer, ProviderListFragment(), null)
                              }, 5000)
        return binding.root
    }

    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(isNavigationShown = false, isBackShown = false, placesShown = false, notificationShown = false, cartShown = false, userName = "Guest", title = "Searching for Provider", subTitle = null, placeName = ""))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
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
        moveToCurrentLocation()
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


    private fun setAddress(lat: Double, long: Double) {
        CalculateAddress(object : CalculateAddress.IAddressListener {
            override fun showLoading() {

            }

            override fun endLoading() {

            }

            override fun addressCalculated(result: String?) {
                binding.tvAddress.text = result
            }

        }).execute(lat, long)
    }


}