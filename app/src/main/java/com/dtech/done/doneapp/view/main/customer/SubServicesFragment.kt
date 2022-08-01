package com.dtech.done.doneapp.view.main.customer

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.data.model.responsemodel.Categories
import com.dtech.done.doneapp.data.model.responsemodel.SubServices
import com.dtech.done.doneapp.databinding.FragmentSubServicesBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.SearchItemsListAdapter
import com.dtech.done.doneapp.viewmodel.ServicesViewModel
import java.util.*
import kotlin.collections.ArrayList


class SubServicesFragment :BaseFragment() {
    lateinit var binding: FragmentSubServicesBinding
    private lateinit var viewModel: ServicesViewModel
    var servicesCategoriesList:ArrayList<Categories> = ArrayList()
    var serviceId: Int = 0
    private var subCategoryServiceTitle :String = ""

    companion object {
        lateinit var instance: SubServicesFragment
        var subServices: SubServices ?= null


        fun newInstance(subServices: SubServices): SubServicesFragment {
            instance = SubServicesFragment()
            this.subServices = subServices
            return instance
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_services, container, false)
        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        setData()
        setUiObserver()
        setToolbar()
        callGetServiceCategoriesList()
       return binding.root
    }

    private fun setData(){
        serviceId = subServices!!.serviceId!!
        subCategoryServiceTitle = subServices!!.serviceTitle!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvDescription.text = Html.fromHtml(subServices!!.serviceDetail!! , Html.FROM_HTML_MODE_LEGACY)
        }else {
            binding.tvDescription.text = Html.fromHtml(subServices!!.serviceDetail!!)
        }

        Handler(Looper.myLooper()!!).postDelayed({
            binding.tvDescriptionTitle.visibility = View.GONE
            binding.tvDescription.visibility = View.GONE
        },2000)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.etSearch.text.toString().isNullOrEmpty()){
                    setServiceCategoriesListAdapter(servicesCategoriesList)
                }else {
                    var filterList = ArrayList(servicesCategoriesList.filter { it.categoryTitle!!.toLowerCase(Locale.ROOT).startsWith(binding.etSearch.text.toString().lowercase(Locale.ROOT)) })
                    if (filterList.isNullOrEmpty()){
                        setServiceCategoriesListAdapter(arrayListOf())
                    }else {
                        setServiceCategoriesListAdapter(filterList)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setUiObserver() {
        viewModel.failureMessage.observe(viewLifecycleOwner, Observer {
            it?.let { msg ->
                ActivityBase.activity.showToastMessage(msg)
            }
        })
        viewModel.serviceCategoriesResponse.observe(viewLifecycleOwner, Observer {

            if (!it.categories.isNullOrEmpty()){
                servicesCategoriesList = it.categories
                setServiceCategoriesListAdapter(servicesCategoriesList)
            }

        })

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            ActivityBase.activity.supportFragmentManager.popBackStackImmediate()
           // callGetServiceCategoriesList()

        })
    }
    private fun setServiceCategoriesListAdapter(servicesCategoriesList: ArrayList<Categories>) {
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(activity)
            binding.rvSearch.adapter = activity?.let {
                SearchItemsListAdapter(it,servicesCategoriesList, object : SearchItemsListAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        addFragment(R.id.mainContainer , SelectServiceTypeFragment.newInstance(serviceId,servicesCategoriesList[position].categoryId!!,subCategoryServiceTitle,
                            servicesCategoriesList[position].categoryTitle.toString()
                        ) , "SelectServiceTypeFragment")
                    }
                })
            }
        }

    }
    private fun callGetServiceCategoriesList(){
        subServices!!.serviceId?.let { viewModel.getServiceCategoriesList(it) }
    }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", subCategoryServiceTitle, null, ""))
    }
}