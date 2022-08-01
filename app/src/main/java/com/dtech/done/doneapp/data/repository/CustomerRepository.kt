package com.dtech.done.doneapp.data.repository

import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.data.model.requestmodel.DeleteAddressRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.SaveAddressRequestModel
import com.dtech.done.doneapp.data.model.requestmodel.UpdateAddressRequestModel
import com.dtech.done.doneapp.data.model.responsemodel.GeneralModel
import com.dtech.done.doneapp.data.model.responsemodel.GetCustomerAddressesResponseModel
import com.dtech.done.doneapp.data.remote.SingleEnqueueCall
import com.dtech.done.doneapp.data.remote.callback.IGenericCallBack
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.enums.UserType
import com.dtech.done.doneapp.utilities.helper.SingleLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CustomerRepository : IGenericCallBack {

    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var getCustomerResponse: SingleLiveData<ArrayList<GetCustomerAddressesResponseModel>> = SingleLiveData()


    fun getAddresses(){
        when(DoneApp.db.getString(Constants.CONST_USER_TYPE)) {
            UserType.GUEST.value.toString() -> {
                var customerListJson = DoneApp.db.getString(Constants.USER_LOCATION)
                if (!customerListJson.isNullOrEmpty()) {
                    val itemType = object : TypeToken<ArrayList<GetCustomerAddressesResponseModel>>() {}.type
                    var addressList: ArrayList<GetCustomerAddressesResponseModel> = Gson().fromJson<ArrayList<GetCustomerAddressesResponseModel>>(customerListJson, itemType)
                    if (!addressList.isNullOrEmpty()) {
                        getCustomerResponse.postValue(addressList)
                    }
                }
            }
            UserType.CUSTOMER.value.toString() -> {
                val call = DoneApp.apiService.getCustomerAddress()
                SingleEnqueueCall.callRetrofit(call, Constants.GET_CUSTOMER_ADDRESSES, true, this)
            }
        }
    }

    fun saveAddress(obj : SaveAddressRequestModel){
        val call = DoneApp.apiService.saveAddress(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.SAVE_ADDRESS, true, this)
    }

    fun updateAddress(obj : UpdateAddressRequestModel){
        when(DoneApp.db.getString(Constants.CONST_USER_TYPE)) {
            UserType.GUEST.value.toString() -> {
                var customerListJson = DoneApp.db.getString(Constants.USER_LOCATION)
                if (!customerListJson.isNullOrEmpty()) {
                    val itemType = object : TypeToken<ArrayList<GetCustomerAddressesResponseModel>>() {}.type
                    var addressList: ArrayList<GetCustomerAddressesResponseModel> = Gson().fromJson<ArrayList<GetCustomerAddressesResponseModel>>(customerListJson, itemType)
                    if (!addressList.isNullOrEmpty()) {
                        var index = addressList.indexOfFirst { it.addressIdLocal.equals(obj.addressId) }
                        if (index >= 0) {
                            addressList[index].addressIdLocal = obj.addressId!!
                            addressList[index].addressName = obj.addressName!!
                            addressList[index].addressTitle = obj.addressTitle!!
                            addressList[index].floor = obj.floor!!
                            addressList[index].latitude = obj.latitude.toString()
                            addressList[index].longitude = obj.longitude.toString()
                            addressList[index].providerNote = obj.providerNote!!
                            addressList[index].street = obj.street!!
                            //addressList.removeAt(index)
                        }
                        DoneApp.db.putString(Constants.USER_LOCATION , Gson().toJson(addressList))
                        generalResponse.postValue("")
                    }
                }else {
                    generalResponse.postValue("")
                }

            }
            UserType.CUSTOMER.value.toString() -> {
                val call = DoneApp.apiService.updateAddress(obj)
                SingleEnqueueCall.callRetrofit(call, Constants.UPDATE_ADDRESS, true, this)
            }
        }

    }

    fun deleteAddress(id : Int , idLocal : String){
        when(DoneApp.db.getString(Constants.CONST_USER_TYPE)) {
            UserType.GUEST.value.toString() -> {
                var customerListJson = DoneApp.db.getString(Constants.USER_LOCATION)
                if (!customerListJson.isNullOrEmpty()) {
                    val itemType = object : TypeToken<ArrayList<GetCustomerAddressesResponseModel>>() {}.type
                    var addressList: ArrayList<GetCustomerAddressesResponseModel> = Gson().fromJson<ArrayList<GetCustomerAddressesResponseModel>>(customerListJson, itemType)
                    if (!addressList.isNullOrEmpty()) {
                        var index = addressList.indexOfFirst { it.addressIdLocal.equals(idLocal) }
                        if (index >= 0) {
                            addressList.removeAt(index)
                        }
                        DoneApp.db.putString(Constants.USER_LOCATION , Gson().toJson(addressList))
                        getCustomerResponse.postValue(addressList)
                    }
                }else {
                    getCustomerResponse.postValue(arrayListOf())
                }

            }

            UserType.CUSTOMER.value.toString() -> {
                val call = DoneApp.apiService.deleteAddress(DeleteAddressRequestModel(id))
                SingleEnqueueCall.callRetrofit(call, Constants.DELETE_ADDRESS, true, this)
            }
        }
    }

    override fun success(apiName: String, response: Any?) {
        when(apiName){
            Constants.GET_CUSTOMER_ADDRESSES -> {
                val responseModel = response as ArrayList<GetCustomerAddressesResponseModel>
                getCustomerResponse.postValue(responseModel)
            }

            Constants.SAVE_ADDRESS -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.DELETE_ADDRESS -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.UPDATE_ADDRESS -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }
        }
    }

    override fun failure(apiName: String, message: String?) {
        failureMessage.postValue(message!!)
    }
}