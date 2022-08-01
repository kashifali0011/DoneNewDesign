package com.dtech.done.doneapp.view.main.customer

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.remote.callback.ICallBackUri
import com.dtech.done.doneapp.databinding.FragmentEditCustomerProfileBinding
import com.dtech.done.doneapp.utilities.extensions.processCapturedPhoto
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.utilities.extensions.startCamera
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.MediaBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class EditCustomerProfileFragment: BaseFragment(), View.OnClickListener,
    MediaBottomSheetFragment.ISelectListener {
    lateinit var binding:FragmentEditCustomerProfileBinding
    var isEnglish = true
    private val REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_customer_profile, container, false)
        setListener()
        setToolbar()
        return binding.root
    }
    private fun setListener() {
        binding.ivEditName.setOnClickListener(this)
        binding.ivEditEmail.setOnClickListener(this)
        binding.ivEditMobileNum.setOnClickListener(this)
        binding.ivEditLoginPin.setOnClickListener(this)
        binding.ivLanguage.setOnClickListener(this)
        binding.btnUpdatePic.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivLanguage -> {
                if (isEnglish) {
                    isEnglish = false
                    binding.ivLanguage.setImageResource(R.drawable.ic_arabic_switch)
                } else {
                    isEnglish = true
                    binding.ivLanguage.setImageResource(R.drawable.ic_english_switch)
                }
            }
            R.id.btnUpdatePic ->{
                showBottomSheet()
            }
            R.id.ivEditName ->{
                addFragment(R.id.mainContainer, EditFullNameFragment(), "EditFullNameFragment")
                binding.ivEditName.visibility = View.VISIBLE
            }
            R.id.ivEditEmail ->{
                addFragment(R.id.mainContainer, EditEmailFragment(), "EditEmailFragment")

            }
            R.id.ivEditMobileNum ->{
                addFragment(R.id.mainContainer, EditMobileNumberFragment(), "EditMobileNumberFragment")

            }
            R.id.ivEditLoginPin ->{
                addFragment(R.id.mainContainer, EditLoginPinFragment(), "EditLoginPinFragment")

            }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FishBun.FISHBUN_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    var path = data!!.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)
                    Glide.with(requireActivity())
                        .load(path) // Uri of the picture
                    .into(binding.ivProfilePic);
                }
            }

            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    ActivityBase.activity.processCapturedPhoto(object : ICallBackUri {
                        override fun imageUri(imagePath: Uri?) {
                            Glide.with(requireActivity())
                                .load(imagePath) // Uri of the picture
                                .into(binding.ivProfilePic);
                        }
                    })
                }
            }
        }

    }

    private fun showBottomSheet(){
        var  bottomSheetListFragment = MediaBottomSheetFragment(arrayListOf("Camera" , "Gallery") , "Cancel")
        bottomSheetListFragment.setMyListener(this)
        if (!bottomSheetListFragment.isAdded) {
            bottomSheetListFragment.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "", null, ""))
    }
    private fun showGallery(){
        FishBun.with(ActivityBase.activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setActionBarColor(
                Color.parseColor("#E2F3FA"),
                Color.parseColor("#E2F3FA"),
                true
            )
            .setActionBarTitleColor(Color.parseColor("#474747"))
            .isStartInAllView(false)
            .startAlbum()
    }
    override fun onMediaSelect(pos: Int) {
        when(pos){
            0-> {
                ActivityBase.activity.startCamera(REQUEST_CODE)
            }
            1 -> {
                showGallery()
            }
        }
    }

    override fun onMediaCancel() {
    }
    private fun validateInput() {
        if (binding.etPhoneNum.text.toString().length < 9){
                requireActivity().showToastMessage("The phone number must have 9 digits")
        }
    }
}