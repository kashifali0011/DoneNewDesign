package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCustomerAddressesResponseModel(
    @SerializedName("address_id")
    var addressId: Int,
    @SerializedName("address_name")
    var addressName: String?,
    @SerializedName("address_title")
    var addressTitle: String?,
    @SerializedName("floor")
    var floor: String?,
    @SerializedName("latitude")
    var latitude: String?,
    @SerializedName("longitude")
    var longitude: String?,
    @SerializedName("provider_note")
    var providerNote: String?,

    @SerializedName("street")
    @Expose
    var street: String?,

 /*   @SerializedName("street")
    var street: String?,*/

    var isSelect :Boolean,
    var addressIdLocal : String = ""
)