package com.dtech.done.doneapp.data.repository

import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.data.model.requestmodel.ServiceCategoriesDetailFormRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.ServiceCategoriesRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.*
import com.dtech.done.doneapp.data.remote.SingleEnqueueCall
import com.dtech.done.doneapp.data.remote.callback.IGenericCallBack
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.helper.SingleLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import java.io.File
import java.util.logging.Level.parse


class ServicesRepository : IGenericCallBack {
    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var getServicesListResponse: SingleLiveData<ServiceData> = SingleLiveData()
    var getServiceCategories: SingleLiveData<ServiceCategories> = SingleLiveData()
    var getServiceDetailForm: SingleLiveData<Data> = SingleLiveData()
    var uploadImage :SingleLiveData<ArrayList<String>> = SingleLiveData()

    fun getAllServicesList() {
        val call = DoneApp.apiService.getAllServicesList()
        SingleEnqueueCall.callRetrofit(call, Constants.GET_ALL_SERVICES_LIST, true, this)
    }

    fun getServiceCategories(serviceId: Int) {
        val call =
            DoneApp.apiService.getServicesCategories(ServiceCategoriesRequestModel(serviceId))
        SingleEnqueueCall.callRetrofit(call, Constants.GET_SERVICE_CATEGORIES, true, this)
    }

    fun getServiceCategoriesDetailForm(serviceId: Int, categoriesId: Int) {
        val call = DoneApp.apiService.getServiceCategoriesDetailForm(
            ServiceCategoriesDetailFormRequestModel(serviceId, categoriesId)
        )
        SingleEnqueueCall.callRetrofit(call, Constants.GET_SERVICE_DETAIL_FORM, true, this)

    }
    fun uploadImage(imagePath:ArrayList<MultipartBody.Part?>){
        val call = DoneApp.apiService.uploadImage(imagePath)
        SingleEnqueueCall.callRetrofit(call,Constants.UPLOAD_IMAGE,true,this)
    }
    override fun success(apiName: String, response: Any?) {
        when (apiName) {
            Constants.GET_ALL_SERVICES_LIST -> {
                val responseModel = response as ServiceData
                getServicesListResponse.postValue(responseModel)
            }
            Constants.GET_SERVICE_CATEGORIES -> {
                val responseModel = response as ServiceCategories
                getServiceCategories.postValue(responseModel)
            }

            Constants.GET_SERVICE_DETAIL_FORM -> {
                val responseModel = response as Data
                getServiceDetailForm.value = responseModel
            }

            Constants.UPLOAD_IMAGE ->{
                val responseModel = response as ArrayList<String>
                uploadImage.postValue(responseModel)
            }
        }
    }

    override fun failure(apiName: String, message: String?) {
        failureMessage.postValue(message!!)
    }
}