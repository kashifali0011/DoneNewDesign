package com.dtech.done.doneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dtech.done.doneapp.data.model.requestmodel.CreatePinRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.ServiceCategoriesRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.*
import com.dtech.done.doneapp.data.repository.ServicesRepository
import com.dtech.done.doneapp.utilities.helper.SingleLiveData
import okhttp3.MultipartBody

class ServicesViewModel(application: Application) : AndroidViewModel(application) {
    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var getServicesListResponse: SingleLiveData<ServiceData> = SingleLiveData()
    var serviceCategoriesResponse:SingleLiveData<ServiceCategories> = SingleLiveData()
    var serviceDetailFormResponse : SingleLiveData<Data> =  SingleLiveData()
    var uploadImage : SingleLiveData<ArrayList<String>> =  SingleLiveData()

    private var repository = ServicesRepository()
    init {
        failureMessage = repository.failureMessage
        generalResponse = repository.generalResponse
        getServicesListResponse = repository.getServicesListResponse
        serviceCategoriesResponse = repository.getServiceCategories
        serviceDetailFormResponse = repository.getServiceDetailForm
      //  uploadImage =  repository.uploadImage
    }

    fun getServicesList(){
        repository.getAllServicesList()
    }

    fun getServiceCategoriesList(serviceId : Int){
        repository.getServiceCategories(serviceId)
    }

    fun getServiceDetailForm(serviceId : Int,categoriesId : Int){
        repository.getServiceCategoriesDetailForm(serviceId,categoriesId)
    }
    fun uploadImage(imageUrl:ArrayList<MultipartBody.Part?>){
        repository.uploadImage(imageUrl)
    }

}