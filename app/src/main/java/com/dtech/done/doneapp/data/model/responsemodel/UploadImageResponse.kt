package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(
    @SerializedName("image_url" ) var imageUrl : String? = null
)