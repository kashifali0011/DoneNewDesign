package com.dtech.done.doneapp.data.model.responsemodel

import com.google.gson.annotations.SerializedName

data class ServiceCategoriesDetailForm(

     @SerializedName("status_code" ) var statusCode : Int?    = null,
     @SerializedName("message"     ) var message    : String? = null,
     @SerializedName("data"        ) var data       : Data?   = Data()
 )
data class Questiontree (
    @SerializedName("question_title" ) var questionTitle : String?               = null,
    @SerializedName("question_id"    ) var questionId    : Int?                  = null,
    @SerializedName("option_id"      ) var optionId      : Int?                  = null,
    @SerializedName("optiontree"     ) var optiontree    : ArrayList<Optiontree> = arrayListOf(),
    var parentId: String = ""
)

data class Optiontree (

    @SerializedName("option"       ) var option       : String?                 = null,
    @SerializedName("id"           ) var id           : Int?                    = 0,
    @SerializedName("question_id"  ) var questionId   : Int?                    = null,
    @SerializedName("questiontree" ) var questiontree : ArrayList<Questiontree> = arrayListOf()

)

data class Service (

    @SerializedName("service_title" ) var serviceTitle : String? = null,
    @SerializedName("is_home"       ) var isHome       : Int?    = null,
    @SerializedName("is_pick"       ) var isPick       : Int?    = null

)
data class Data (

    @SerializedName("service_questions" ) var serviceQuestions : ArrayList<Questiontree> = null ?: arrayListOf(),
    @SerializedName("service"           ) var service          : Service?                    = Service(),
    @SerializedName("option_text"       ) var optionText       : String?                     = null

)