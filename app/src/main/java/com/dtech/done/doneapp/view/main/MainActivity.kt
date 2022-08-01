package com.dtech.done.doneapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.ActivityMainBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.extensions.*
import com.dtech.done.doneapp.utilities.helper.Permissions
import com.dtech.done.doneapp.view.auth.AuthActivity
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.main.customer.*
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MainActivity : ActivityBase(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    var isEnglish = true
    private lateinit var mListener: IPlaceSetListener
    var userType = DoneApp.db.getString(Constants.CONST_USER_TYPE)!!.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setData()
        Permissions.checkPermission(activity)
        setListener()
        addFragment(R.id.mainContainer, HomeFragmentCustomer(), null)
    }

    private fun setData(){
        when(userType){
            UserType.GUEST.value -> {
                binding.isGuest = true
            }

            UserType.CUSTOMER.value -> {
                binding.isGuest = false
            }

            UserType.PROVIDER.value -> {
                binding.isGuest = false
            }
        }
    }
    private fun setListener() {
        binding.ivBack.setOnClickListener(this)
        binding.ivNavigation.setOnClickListener(this)
        binding.ivNotification.setOnClickListener(this)
        binding.ivCart.setOnClickListener(this)
        binding.tvPlaceName.setOnClickListener(this)
        binding.ivCloseDrawer.setOnClickListener(this)
        binding.ivLanguage.setOnClickListener(this)
        binding.tvPolicy.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.llDeleteAccount.setOnClickListener(this)
        binding.btnGetStarted.setOnClickListener(this)
        binding.llOrders.setOnClickListener(this)
        binding.llCart.setOnClickListener(this)
        binding.llInvoice.setOnClickListener(this)
        binding.llLocation.setOnClickListener(this)
        binding.llWallet.setOnClickListener(this)
        binding.llLogout.setOnClickListener(this)

    }

    fun setToolbar(toolbarModel: ToolbarModel) {
        binding.toolbarModel = toolbarModel
    }

    fun setPlaceName(placeName: String) {
        var model = binding.toolbarModel!!
        model.placeName = placeName
        binding.toolbarModel = model
    }

    fun setPlaceOnMap(listener: IPlaceSetListener) {
        mListener = listener
    }

    interface IPlaceSetListener {
        fun onPlaceSelect(lat: Double, long: Double)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.ivBack -> {
                binding.root.hideKeyboard()
                onBackPressed()
            }
            R.id.ivNavigation -> {
                binding.dlMain.openDrawer(GravityCompat.START)
            }

            R.id.ivNotification -> {
                addFragment(R.id.mainContainer, NotificationFragment(), "NotificationFragment")
            }

            R.id.ivCart -> {
                addFragment(R.id.mainContainer, MyCartFragment(), "MyCartFragment")
            }

            R.id.tvPlaceName -> {
                openPlaceIntent()
            }

            R.id.ivCloseDrawer -> {
                binding.dlMain.closeDrawer(GravityCompat.START)
            }

            R.id.btnGetStarted ->{
              /*  var title = getString(R.string.log_out)
                var msg = getString(R.string.are_you_sure_want_to_log_out)
                confirmDialogBox(title,msg,this)*/
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }

            R.id.ivLanguage -> {
                if (isEnglish) {
                    isEnglish = false
                    binding.ivLanguage.setImageResource(R.drawable.ic_arabic_switch)
                } else {
                    isEnglish = true
                    binding.ivLanguage.setImageResource(R.drawable.ic_english_switch)
                }
                binding.dlMain.closeDrawer(GravityCompat.START)


            }
            R.id.llOrders ->{
                binding.dlMain.closeDrawer(GravityCompat.START)
                addFragment(R.id.mainContainer, MyOrdersFragment(),"MyOrdersFragment")
            }
            R.id.llCart ->{
                binding.dlMain.closeDrawer(GravityCompat.START)
                addFragment(R.id.mainContainer, MyCartFragment(),"MyCartFragment")
            }
            R.id.llInvoice ->{
                binding.dlMain.closeDrawer(GravityCompat.START)
                addFragment(R.id.mainContainer, AdditionalInvoiceFragment(),"AdditionalInvoiceFragment")
            }
            R.id.llLocation ->{
                binding.dlMain.closeDrawer(GravityCompat.START)
                addFragment(R.id.mainContainer, MyLocationFragment(),"MyLocationFragment")
            }
            R.id.llWallet ->{
                binding.dlMain.closeDrawer(GravityCompat.START)
                addFragment(R.id.mainContainer, WalletFragment(),"WalletFragment")
            }
            R.id.tvPolicy -> {
                binding.dlMain.closeDrawer(GravityCompat.START)
                webViewDialogBox(Constants.PRIVACY_URL_EN)

            }
            R.id.tvTerms -> {
                binding.dlMain.closeDrawer(GravityCompat.START)
                webViewDialogBox(Constants.TERMS_URL_EN)
                //ratingDialog()
            }
            R.id.llDeleteAccount -> {
                 deleteAccountDialogBox()
            }
            R.id.llLogout ->{
                 var title = getString(R.string.log_out)
                var msg = getString(R.string.are_you_sure_want_to_log_out)
                confirmDialogBox(title,msg)
            }
        }
    }

    fun checkVisibleFragment() {
        binding.root.hideKeyboard()

        when (val fragment = activity.supportFragmentManager.findFragmentById(R.id.mainContainer)) {
            is HomeFragmentCustomer -> {
                fragment.setToolbar()
            }
            is SelectLocationFragment -> {
                fragment.setToolbar()
            }
            is SubServicesFragment -> {
                fragment.setToolbar()
            }
            is SelectServiceTypeFragment -> {
                fragment.setToolbar()
            }
            is SelectServicesFragment -> {
                fragment.setToolbar()
            }

            is SearchProviderFragment -> {
                fragment.setToolbar()
            }
            is ProviderListFragment -> {
                fragment.setToolbar()
            }
            is ProviderDetailFragment -> {
                fragment.setToolbar()
            }
            is MyCartFragment -> {
                fragment.setToolbar()
            }
            is MyOrdersFragment -> {
                fragment.setToolbar()
            }
            is WalletFragment -> {
                fragment.setToolbar()
            }
            is AdditionalInvoiceFragment -> {
                fragment.setToolbar()
            }
            is MyCartPreviewFragment -> {
                fragment.setToolbar()
            }
            is SuccessOrderPlaceFragment -> {
                fragment.setToolbar()
            }
            is MyLocationFragment -> {
                fragment.setToolbar()
            }
            is EditCustomerProfileFragment -> {
                fragment.setToolbar()
            }
            is EditFullNameFragment ->{
                fragment.setToolbar()
            }
            is EditEmailFragment ->{
                fragment.setToolbar()
            }
            is EditLoginPinFragment ->{
                fragment.setToolbar()
            }
            is EditMobileNumberFragment ->{
                fragment.setToolbar()
            }
            is NotifyEmailVerificationFragment ->{
                fragment.setToolbar()
            }
            is AdditionalInvoicePaymentConfirmationFragment ->{
                fragment.setToolbar()
            }
            is NoProviderFragment ->{
                fragment.setToolbar()
            }
        }
    }

    private fun openPlaceIntent() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .setHint("Search Location")
            .build(this)
        placesActivityResultLauncher.launch(intent)
    }

    var placesActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            var resultCode = result.resultCode
            var data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        setPlaceName(place.name)
                        mListener.onPlaceSelect(place.latLng.latitude, place.latLng.longitude)
                        Log.i("MainActivity", "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("MainActivity", status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (val fragment = activity.supportFragmentManager.findFragmentById(R.id.mainContainer)) {

            is SelectServiceTypeFragment -> {
                fragment.onActivityResult(requestCode, resultCode, data)
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

    }
}

