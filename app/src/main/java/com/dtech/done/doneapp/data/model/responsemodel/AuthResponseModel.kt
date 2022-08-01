package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName


data class VerifyOtpResponseModel(@SerializedName("step") var step: Int = 0)

data class LoginUserResponseModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("user_type") var userType: String? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("token") var token: Token? = null
)

data class Token(

    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("token_type") var tokenType: String? = null,
    @SerializedName("expires_in") var expiresIn: Int? = null

)

data class User(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("user_type") var userType: String? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("token") var token: Token? = null

)

