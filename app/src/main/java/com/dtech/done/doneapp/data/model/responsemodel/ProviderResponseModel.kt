package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

data class GetProviderData (

    @SerializedName("providers" ) var providers : ArrayList<Providers> = arrayListOf()

)

data class ProviderDetail (

    @SerializedName("user_id"        ) var userId        : Int?    = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("phone"          ) var phone         : String? = null,
    @SerializedName("crNumber"       ) var crNumber      : String? = null,
    @SerializedName("description"    ) var description   : String? = null,
    @SerializedName("logo_image"     ) var logoImage     : String? = null,
    @SerializedName("working_radius" ) var workingRadius : Int?    = null

)


data class ProviderAddress (

    @SerializedName("user_id"       ) var userId       : String? = null,
    @SerializedName("latitude"      ) var latitude     : String? = null,
    @SerializedName("longitude"     ) var longitude    : String? = null,
    @SerializedName("address_title" ) var addressTitle : String? = null

)

data class UserData (

    @SerializedName("id" ) var id : Int? = null

)

data class Providers (

    @SerializedName("service_type"        ) var serviceType       : Int?             = null,
    @SerializedName("is_home"             ) var isHome            : Int?             = null,
    @SerializedName("is_pick"             ) var isPick            : Int?             = null,
    @SerializedName("provider_service_id" ) var providerServiceId : Int?             = null,
    @SerializedName("service_id"          ) var serviceId         : String?          = null,
    @SerializedName("provider_id"         ) var providerId        : String?          = null,
    @SerializedName("rating"              ) var rating            : String?          = null,
    @SerializedName("service_price"       ) var servicePrice      : String?          = null,
    @SerializedName("provider_detail"     ) var providerDetail    : ProviderDetail?  = ProviderDetail(),
    @SerializedName("provider_address"    ) var providerAddress   : ProviderAddress? = ProviderAddress(),
    @SerializedName("user"                ) var user              : UserData?            = UserData()

)