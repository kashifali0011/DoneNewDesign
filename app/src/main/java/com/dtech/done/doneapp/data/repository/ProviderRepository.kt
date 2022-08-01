package com.dtech.done.doneapp.data.repository

import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderDetailRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.GetProviderRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderData
import com.dtech.done.doneapp.data.model.responsemodel.GetProviderDetailResponseModel
import com.dtech.done.doneapp.data.remote.SingleEnqueueCall
import com.dtech.done.doneapp.data.remote.callback.IGenericCallBack
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.helper.SingleLiveData

class ProviderRepository: IGenericCallBack {
    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var getProviderList: SingleLiveData<GetProviderData> = SingleLiveData()
    var getProviderDetails: SingleLiveData<GetProviderDetailResponseModel> = SingleLiveData()


    fun getProviderList(obj : GetProviderRequestModel){
        val call = DoneApp.apiService.getProviderList(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.GET_PROVIDER ,true, this)
    }

    fun getProviderDetails(obj: GetProviderDetailRequestModel){
       val call = DoneApp.apiService.getProviderDetails(obj)
        SingleEnqueueCall.callRetrofit(call , Constants.GET_TIME_SLOT , true , this)
    }

    override fun success(apiName: String, response: Any?) {
        when(apiName) {
            Constants.GET_PROVIDER -> {
                val responseModel = response as GetProviderData
                getProviderList.postValue(responseModel)
            }
            Constants.GET_TIME_SLOT ->{
                val responseModel = response as GetProviderDetailResponseModel
                getProviderDetails.postValue(responseModel)
            }
        }
    }

    override fun failure(apiName: String, message: String?) {
        failureMessage.postValue(message!!)
    }
}