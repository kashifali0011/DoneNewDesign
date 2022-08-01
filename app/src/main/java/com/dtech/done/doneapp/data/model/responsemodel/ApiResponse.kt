package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ApiResponse<T> {
    @SerializedName("status_code")
    @Expose
    var code: Int = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: T? = null
}
