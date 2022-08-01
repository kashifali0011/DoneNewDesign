package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SetPositionCategoryModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentSelectServicesBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.BannerSliderAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.CategoryAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.ServicesAdapter
import com.dtech.done.doneapp.viewmodel.ServicesViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dtech.done.doneapp.data.model.responsemodel.MainServices

class SelectServicesFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentSelectServicesBinding
    var bannerSliderAdapter: BannerSliderAdapter? = null
    var categoryAdapter : CategoryAdapter? = null
    var serviceAdapter : ServicesAdapter? = null
    val arrayList: ArrayList<SetPositionCategoryModel> = ArrayList()
    var mainServicesList:ArrayList<MainServices> = ArrayList()
    private lateinit var viewModel: ServicesViewModel
    private var mainServiceTitle :String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_services, container, false)
        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        callGetServiceList()
        setUiObserver()
        getBannerSlier()
        setToolbar()
        setListener()
        return binding.root
    }

    private fun setUiObserver() {
        viewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })
        viewModel.getServicesListResponse.observe(viewLifecycleOwner, Observer {

            if (!it.mainServices.isNullOrEmpty()){
                mainServicesList = it.mainServices
                mainServiceTitle = mainServicesList[0].serviceTitle!!
                setToolbar()
                mainServicesList[0].isCheck = true
                setMainCategoryServiceAdapter()
                if (!it.mainServices[0].subServices.isNullOrEmpty())
                    setSubCategoryServicesAdapter(0)
            }

        })

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            ActivityBase.activity.supportFragmentManager.popBackStackImmediate()

        })
    }

    private fun getBannerSlier() {
        val bannerLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvSelectServicesSlider.layoutManager = bannerLayoutManager
        bannerSliderAdapter = BannerSliderAdapter(requireActivity())
        binding.rvSelectServicesSlider.adapter =  bannerSliderAdapter

    }
    private fun setMainCategoryServiceAdapter(){

        val categoryLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvMainCategory.layoutManager = categoryLayoutManager
        categoryAdapter = CategoryAdapter(requireActivity(),mainServicesList,object :CategoryAdapter.OnItemClickListener{
            override fun onSelectedItem(position: Int) {
                mainServiceTitle = mainServicesList[position].serviceTitle!!
                setToolbar()
                setSubCategoryServicesAdapter(position)
           }
        })
        binding.rvMainCategory.adapter = categoryAdapter
    }
    private fun setSubCategoryServicesAdapter(index: Int){
        val servicesLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        binding.rvSubCategory.layoutManager = servicesLayoutManager
        serviceAdapter =  ServicesAdapter(requireActivity(),mainServicesList[index].subServices, object : ServicesAdapter.OnItemClickListener {
            override fun onSubServiceItemClickListener(position: Int) {
                addFragment(R.id.mainContainer , SubServicesFragment.newInstance(mainServicesList[index].subServices[position]) , "SubServicesFragment")
            }
        })
        binding.rvSubCategory.adapter = serviceAdapter
    }


    private fun setListener() {
      //  binding.rlSearchLocation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
           /* R.id.rlSearchLocation -> {

            }*/
        }

    }
   private fun callGetServiceList(){
       viewModel.getServicesList()
   }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", mainServiceTitle, null, ""))
    }
}