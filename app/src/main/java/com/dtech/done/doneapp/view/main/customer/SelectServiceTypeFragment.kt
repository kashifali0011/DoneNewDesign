package com.dtech.done.doneapp.view.main.customer

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.responsemodel.*
import com.dtech.done.doneapp.data.remote.callback.ICallBackUri
import com.dtech.done.doneapp.data.remote.callback.ICallBackUriMultiple
import com.dtech.done.doneapp.databinding.FragmentSelectServiceTypeBinding
import com.dtech.done.doneapp.utilities.extensions.*
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.MediaBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.ImageSelectionAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.QuestionAnswerAdapter
import com.dtech.done.doneapp.viewmodel.ServicesViewModel
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.FishBun.Companion.INTENT_PATH
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.logging.Level.parse


class SelectServiceTypeFragment : BaseFragment(), View.OnClickListener,
    MediaBottomSheetFragment.ISelectListener {

    lateinit var binding: FragmentSelectServiceTypeBinding
    lateinit var questionAnswerAdapter: QuestionAnswerAdapter
    private var adapterImageSelection: ImageSelectionAdapter? = null
    private val REQUEST_CODE = 1
    private lateinit var activityResult: ActivityResultLauncher<Intent>
    lateinit var imageList: ArrayList<Uri>
    private lateinit var viewModel: ServicesViewModel
     var uriPath : ArrayList<Uri> = ArrayList()
    var questionTree: ArrayList<Questiontree> = ArrayList()
    var data: Data = Data()
    var optionText :String = ""
    private var  multipartImage: ArrayList<MultipartBody.Part?> = ArrayList()


    companion object {
        lateinit var instance: SelectServiceTypeFragment
        var serviceId: Int = 0
        var categoryId: Int = 0
        private var subCategoryServiceTitle:String = ""
        private var categoryTitle : String = ""


        fun newInstance(serviceID: Int, categoryID: Int, serviceTitleText:String, categoryTitle :String): SelectServiceTypeFragment {
            instance = SelectServiceTypeFragment()
            this.serviceId = serviceID
            this.categoryId = categoryID
            this.subCategoryServiceTitle = serviceTitleText
            this.categoryTitle =  categoryTitle
            return instance
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_service_type,
            container,
            false
        )
        viewModel = ViewModelProvider(this)[ServicesViewModel::class.java]
        binding.rvImageSelection.visibility = View.GONE

        imageList = ArrayList()
        setUiObserver()
        callApiServiceDetailForm()
        setToolbar()
        setData()
        setListener()
        setLauncher()
        return binding.root
    }
      private fun setData(){
      }
    private fun setUiObserver() {
        viewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })
        viewModel.serviceDetailFormResponse.observe(viewLifecycleOwner, Observer {
            questionTree = it.serviceQuestions!!
            optionText = it.optionText.toString()
            if (!questionTree.isNullOrEmpty())
            setQuestionAnswerAdapter()

        })
        viewModel.uploadImage.observe(viewLifecycleOwner, Observer{
            if (!it.isNullOrEmpty()) {

            }
//            addFragment(
//                R.id.mainContainer,
//                SearchProviderFragment(),
//                "SearchProviderFragment"
//            )
        })

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {

        })
    }

    fun setLauncher() {
        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                //  doSomeOperations(data)
            }
        }
    }

    private fun setListener() {
        binding.btnImageUpload.setOnClickListener(this)
        binding.btnProceed.setOnClickListener(this)
        binding.rbServices.setOnClickListener(this)
        binding.rbCollect.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rbServices -> {
                binding.rbServices.isChecked = true
                binding.rbCollect.isChecked = false
                validateInput(false)
            }
            R.id.rbCollect -> {
                binding.rbServices.isChecked = false
                binding.rbCollect.isChecked = true
                validateInput(false)
            }
            R.id.btnImageUpload -> {

                showBottomSheet()
            }

            R.id.btnProceed -> {
                if (validateInput(true)) {
                    callApiUploadImage()
                } else {
                    requireActivity().showToastMessage("Please choose service type and upload at least one image")
                }

            }
        }
    }

    private fun showBottomSheet() {
        var bottomSheetListFragment =
            MediaBottomSheetFragment(arrayListOf("Camera", "Gallery"), "Cancel")
        bottomSheetListFragment.setMyListener(this)
        if (!bottomSheetListFragment.isAdded) {
            bottomSheetListFragment.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }

    private fun showGallery() {
        FishBun.with(ActivityBase.activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(5 - imageList.size)
            .setActionBarColor(
                Color.parseColor("#E2F3FA"),
                Color.parseColor("#E2F3FA"),
                true
            )
            .setActionBarTitleColor(Color.parseColor("#474747"))
            .isStartInAllView(false)
            .startAlbum()
    }

    private fun setImageAdapter() {
        binding.rvImageSelection.apply {
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapterImageSelection = activity?.let {
                ImageSelectionAdapter(
                    it, imageList,
                    object : ImageSelectionAdapter.OnItemClickListener {
                        override fun onDeleteImage(position: Int) {
                            imageList.removeAt(position)
                            setImageAdapter()
                        }

                        override fun addFile(position: Int) {
                            showBottomSheet()
                        }
                    },
                )
            }!!
            binding.rvImageSelection.adapter = adapterImageSelection
            adapterImageSelection!!.notifyDataSetChanged()
        }
    }

    private fun setQuestionAnswerAdapter() {
        binding.rvQuestionAnswer.apply {
            layoutManager = LinearLayoutManager(activity)
          //  (layoutManager as LinearLayoutManager).isAutoMeasureEnabled = true
            questionAnswerAdapter = activity?.let { QuestionAnswerAdapter(it,questionTree,optionText) }!!
            binding.rvQuestionAnswer.adapter = questionAnswerAdapter
            //binding.rvQuestionAnswer.isNestedScrollingEnabled = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FishBun.FISHBUN_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        ActivityBase.activity.processGalleryMultipleImages(data ,object :ICallBackUriMultiple{
                            override fun imageUri(result: ArrayList<Uri>?) {
                                imageList.addAll(result!!)
                                setImageAdapter()
                              //  adapterImageSelection!!.notifyDataSetChanged()
                            }
                        })
                    }
                 /*   uriPath = data!!.getParcelableArrayListExtra<Uri>(INTENT_PATH)!!
                    imageList.addAll(uriPath!!)
                    setImageAdapter()*/
                    /* for (i in 0 until path!!.size){


                     }*/
                }
            }

            REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    ActivityBase.activity.processCapturedPhoto(object : ICallBackUri {
                        override fun imageUri(imagePath: Uri?) {
                            imageList.add(imagePath!!)
                            adapterImageSelection!!.notifyDataSetChanged()
                        }
                    })
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        validateInput(false)
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(
            ToolbarModel(
                false,
                true,
                false,
                false,
                false,
                "",
                subCategoryServiceTitle,
                categoryTitle,
                ""
            )
        )
    }

    override fun onMediaSelect(pos: Int) {
        binding.cvUploadImage.visibility = View.GONE
        binding.rvImageSelection.visibility = View.VISIBLE
        when (pos) {
            0 -> {
                ActivityBase.activity.startCamera(REQUEST_CODE)
            }
            1 -> {
                showGallery()
            }
        }
    }

    override fun onMediaCancel() {
//        binding.rvImageSelection.visibility = View.GONE

    }

    private fun validateInput(isFromButton: Boolean): Boolean {
        if ((binding.rbServices.isChecked || binding.rbCollect.isChecked) && imageList.size >= 1) {
            //callApiUploadImage()
            binding.btnProceed.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.blue)
            return true
        } else {
            binding.btnProceed.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.hint_color)

        }
        return false
    }

    private fun callApiServiceDetailForm() {
        viewModel.getServiceDetailForm(serviceId, categoryId)
    }
    private fun callApiUploadImage(){
        for (index in 0 until imageList.size) {
            val file2 = File(imageList[index].path)
            val reqFile = file2.asRequestBody("image/png".toMediaTypeOrNull())
            var img = MultipartBody.Part.createFormData("file[]",file2.name,reqFile)
            multipartImage.add(img)
        }
        viewModel.uploadImage(multipartImage!!)
    }
}