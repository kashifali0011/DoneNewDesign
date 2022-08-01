package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

data class GetProviderDetailResponseModel(

    @SerializedName("slots") var slots: ArrayList<Slots> = arrayListOf()

)
data class Slots(
    @SerializedName("slot_id") var slotId: Int? = null,
    @SerializedName("week_day") var weekDay: String? = null,
    @SerializedName("slot_time_from") var slotTimeFrom: String? = null,
    @SerializedName("slot_time_to") var slotTimeTo: String? = null,
    @SerializedName("provider_id") var providerId: String? = null,
    @SerializedName("available_resources") var availableResources: Int? = null,
    var isCheck: Boolean = false
)