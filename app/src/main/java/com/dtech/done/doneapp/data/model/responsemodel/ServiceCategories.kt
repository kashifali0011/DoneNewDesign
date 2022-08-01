package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

class ServiceCategories {
    @SerializedName("categories" ) var categories : ArrayList<Categories> = arrayListOf()

}
data class Categories (

    @SerializedName("category_id"      ) var categoryId      : Int?    = null,
    @SerializedName("category_title"   ) var categoryTitle   : String? = null,
    @SerializedName("category_summary" ) var categorySummary : String? = null,
    @SerializedName("category_detail"  ) var categoryDetail  : String? = null,
    @SerializedName("category_order"   ) var categoryOrder   : String? = null

)