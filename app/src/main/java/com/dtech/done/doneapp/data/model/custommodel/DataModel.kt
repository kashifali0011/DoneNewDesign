package com.dtech.done.doneapp.data.model.custommodel

data class ToolbarModel(var isNavigationShown: Boolean, var isBackShown: Boolean, var placesShown: Boolean, var notificationShown: Boolean, var cartShown: Boolean, var userName: String, var title: String, var subTitle: String?, var placeName: String)

data class SetPositionCategoryModel(var isChecked: Boolean = false)
data class SaveCouponModel(var coupon: String = "")
data class SaveCouponApplyModel(var coupon: String = "")
data class SaveWalletLayoutModel(var checkStatus: Int = 0)
data class SaveNotificationModel(var checkStatus: Int = 0)
data class SaveLocationStatusModel(var check: Int = 0)
data class ProviderOptionList(var number: Int = 0)

