package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

data class ServiceData (

    @SerializedName("main_services" ) var mainServices : ArrayList<MainServices> = arrayListOf(),

    )


data class SubServices (

    @SerializedName("service_id"      ) var serviceId     : Int?    = null,
    @SerializedName("service_title"   ) var serviceTitle  : String? = null,
    @SerializedName("service_detail"  ) var serviceDetail : String? = null,
    @SerializedName("service_icon"    ) var serviceIcon   : String? = null,
    @SerializedName("service_order"   ) var serviceOrder  : String? = null,
    @SerializedName("head_service_id" ) var headServiceId : String? = null

)


data class MainServices (

    @SerializedName("service_id"    ) var serviceId    : Int?                   = null,
    @SerializedName("service_title" ) var serviceTitle : String?                = null,
    @SerializedName("service_icon"  ) var serviceIcon  : String?                = null,
    @SerializedName("service_order" ) var serviceOrder : String?                = null,
    @SerializedName("sub_services"  ) var subServices  : ArrayList<SubServices> = arrayListOf(),
    var isCheck: Boolean = false

)