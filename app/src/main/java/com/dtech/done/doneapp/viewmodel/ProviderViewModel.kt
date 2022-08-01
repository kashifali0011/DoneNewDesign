package com.dtech.done.doneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderDetailRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderData
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderDetailResponseModel
import com.dtech.done.doneapp.data.repository.ProviderRepository
import com.dtech.done.doneapp.utilities.helper.SingleLiveData

class ProviderViewModel(application: Application) : AndroidViewModel(application) {
    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var getProviderList: SingleLiveData<GetProviderData> = SingleLiveData()
    var getProviderDetails: SingleLiveData<GetProviderDetailResponseModel> = SingleLiveData()
    private var repository = ProviderRepository()

    init {
        failureMessage = repository.failureMessage
        getProviderList = repository.getProviderList
        getProviderDetails = repository.getProviderDetails
    }

    fun getProvidersList(obj: GetProviderRequestModel){
        repository.getProviderList(obj)
    }
    fun getProviderDetails(obj: GetProviderDetailRequestModel){
        repository.getProviderDetails(obj)
    }

}