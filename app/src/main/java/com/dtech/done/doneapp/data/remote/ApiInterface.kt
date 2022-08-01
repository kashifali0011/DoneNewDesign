package com.dtech.done.doneapp.data.remote

import com.dtech.done.doneapp.data.model.requestmodel.*
import com.dtech.done.doneapp.data.model.responsemodel.*
import com.dtech.done.doneapp.utilities.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @POST(Constants.VERIFY_PHONE_URL)
    fun verifyPhone(@Body body: VerifyPhoneRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.VERIFY_OTP)
    fun verifyOtp(@Body body: VerifyOtpRequestModel): Call<ApiResponse<VerifyOtpResponseModel>>

    @POST(Constants.BASIC_INFO)
    fun basicInfo(@Body body: BasicInfoRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.CREATE_PIN)
    fun createPin(@Body body: CreatePinRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.LOGIN_URL)
    fun login(@Body body: LoginRequestModel): Call<ApiResponse<LoginUserResponseModel>>

    @POST(Constants.FORGOT_PIN_URL)
    fun forgotPin(@Body body: ForgotPinRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.REFRESH_TOKEN)
    fun refreshToken(): Call<ApiResponse<LoginUserResponseModel>>

    @GET(Constants.GET_CUSTOMER_ADDRESSES)
    fun getCustomerAddress(): Call<ApiResponse<ArrayList<GetCustomerAddressesResponseModel>>>

    @POST(Constants.SAVE_ADDRESS)
    fun saveAddress(@Body body: SaveAddressRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.DELETE_ADDRESS)
    fun deleteAddress(@Body body: DeleteAddressRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.RESEND_OTP)
    fun resendOtp(@Body body: ForgotPinRequestModel): Call<ApiResponse<GeneralModel>>

    @POST(Constants.UPDATE_ADDRESS)
    fun updateAddress(@Body body: UpdateAddressRequestModel): Call<ApiResponse<GeneralModel>>
    @POST(Constants.GET_PROVIDER)
    fun getProviderList(@Body body: GetProviderRequestModel): Call<ApiResponse<GetProviderData>>

    @POST(Constants.GET_TIME_SLOT)
    fun getProviderDetails(@Body body: GetProviderDetailRequestModel): Call<ApiResponse<GetProviderDetailResponseModel>>

    @POST(Constants.GET_ALL_SERVICES_LIST)
    fun getAllServicesList() :Call<ApiResponse<ServiceData>>

    @POST(Constants.GET_SERVICE_CATEGORIES)
    fun getServicesCategories(@Body body: ServiceCategoriesRequestModel):Call<ApiResponse<ServiceCategories>>

    @POST(Constants.GET_SERVICE_DETAIL_FORM)
    fun getServiceCategoriesDetailForm(@Body body:ServiceCategoriesDetailFormRequestModel):Call<ApiResponse<Data>>

    @Multipart
    @POST(Constants.UPLOAD_IMAGE)
    fun uploadImage( @Part file: ArrayList<MultipartBody.Part?>):Call<ApiResponse<ArrayList<String>>>
}