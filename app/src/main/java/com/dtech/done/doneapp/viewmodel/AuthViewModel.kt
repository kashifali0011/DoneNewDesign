package com.dtech.done.doneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dtech.done.doneapp.data.model.requestmodel.*
import com.dtech.done.doneapp.data.model.responsemodel.LoginUserResponseModel
import com.dtech.done.doneapp.data.model.responsemodel.VerifyOtpResponseModel
import com.dtech.done.doneapp.data.repository.AuthRepository
import com.dtech.done.doneapp.utilities.helper.SingleLiveData

/**{
 * Develop By Zeeshan Irfan
 */
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var generalResponse: SingleLiveData<String> = SingleLiveData()
    var verifyOtpResponse: SingleLiveData<VerifyOtpResponseModel> = SingleLiveData()
    var loginResponse: SingleLiveData<LoginUserResponseModel> = SingleLiveData()

    private var repository = AuthRepository()

    init {
        failureMessage = repository.failureMessage
        generalResponse = repository.generalResponse
        verifyOtpResponse = repository.verifyOtpResponse
        loginResponse = repository.loginResponse
    }


    fun verifyPhone(obj: VerifyPhoneRequestModel) {
        repository.verifyPhone(obj)
    }

    fun verifyOtp(obj: VerifyOtpRequestModel) {
        repository.verifyOtp(obj)
    }

    fun basicInfo(obj: BasicInfoRequestModel) {
        repository.basicInfo(obj)
    }

    fun createPin(obj: CreatePinRequestModel) {
        repository.createPin(obj)
    }

    fun login(obj : LoginRequestModel){
        repository.login(obj)
    }

    fun forgotPin(obj : ForgotPinRequestModel){
        repository.forgotPin(obj)
    }

    fun resendOtp(obj : ForgotPinRequestModel){
        repository.resendOtp(obj)
    }


}