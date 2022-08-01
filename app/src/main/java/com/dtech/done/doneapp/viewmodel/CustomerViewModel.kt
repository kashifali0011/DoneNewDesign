package com.dtech.done.doneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dtech.done.doneapp.data.model.requestmodel.SaveAddressRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.UpdateAddressRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.data.repository.CustomerRepository
import com.dtech.done.doneapp.utilities.helper.SingleLiveData

class CustomerViewModel(application: Application) : AndroidViewModel(application)  {

    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var getCustomerResponse: SingleLiveData<ArrayList<GetCustomerAddressesResponseModel>> = SingleLiveData()

    private var repository = CustomerRepository()

    init {
        failureMessage = repository.failureMessage
        generalResponse = repository.generalResponse
        getCustomerResponse = repository.getCustomerResponse
    }

    fun getCustomerAddresses(){
        repository.getAddresses()
    }

    fun saveAddress(obj : SaveAddressRequestModel){
        repository.saveAddress(obj)
    }

    fun deleteAddress(id : Int , localId : String){
        repository.deleteAddress(id,localId)
    }

    fun updateAddress(obj : UpdateAddressRequestModel){
        repository.updateAddress(obj)
    }
}