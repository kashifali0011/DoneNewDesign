package com.dtech.done.doneapp.data.repository

import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.data.model.requestmodel.*
import com.dtech.done.doneapp.data.model.responsemodel.LoginUserResponseModel
import com.dtech.done.doneapp.data.model.responsemodel.VerifyOtpResponseModel
import com.dtech.done.doneapp.data.remote.SingleEnqueueCall
import com.dtech.done.doneapp.data.remote.callback.IGenericCallBack
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.helper.SingleLiveData

class AuthRepository : IGenericCallBack {

    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var verifyOtpResponse: SingleLiveData<VerifyOtpResponseModel> = SingleLiveData()
    var loginResponse: SingleLiveData<LoginUserResponseModel> = SingleLiveData()


    fun verifyPhone(obj : VerifyPhoneRequestModel){
        val call = DoneApp.apiService.verifyPhone(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.VERIFY_PHONE_URL, true, this)
    }

    fun verifyOtp(obj : VerifyOtpRequestModel){
        val call = DoneApp.apiService.verifyOtp(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.VERIFY_OTP, true, this)
    }

    fun basicInfo(obj : BasicInfoRequestModel){
        val call = DoneApp.apiService.basicInfo(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.BASIC_INFO, true, this)
    }

    fun createPin(obj : CreatePinRequestModel){
        val call = DoneApp.apiService.createPin(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.CREATE_PIN, true, this)
    }

    fun login(obj : LoginRequestModel){
        val call = DoneApp.apiService.login(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.LOGIN_URL, true, this)
    }

    fun forgotPin(obj : ForgotPinRequestModel){
        val call = DoneApp.apiService.forgotPin(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.FORGOT_PIN_URL, true, this)
    }

    fun resendOtp(obj : ForgotPinRequestModel){
        val call = DoneApp.apiService.resendOtp(obj)
        SingleEnqueueCall.callRetrofit(call, Constants.RESEND_OTP, true, this)
    }


    override fun success(apiName: String, response: Any?) {
        when(apiName){
            Constants.VERIFY_PHONE_URL -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.BASIC_INFO -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.VERIFY_OTP -> {
                val responseModel = response as VerifyOtpResponseModel
                verifyOtpResponse.postValue(responseModel)
            }

            Constants.CREATE_PIN -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.LOGIN_URL -> {
                val responseModel = response as LoginUserResponseModel
                loginResponse.postValue(responseModel)
            }

            Constants.FORGOT_PIN_URL -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }

            Constants.RESEND_OTP -> {
                val responseModel = response as String
                generalResponse.postValue(responseModel)
            }
        }
    }

    override fun failure(apiName: String, message: String?) {
        failureMessage.postValue(message!!)
    }


}