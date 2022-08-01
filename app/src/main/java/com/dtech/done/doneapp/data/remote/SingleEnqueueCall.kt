package com.dtech.done.doneapp.data.remote

import android.widget.Toast
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.responsemodel.ApiResponse
import com.dtech.done.doneapp.data.model.responsemodel.GeneralModel
import com.dtech.done.doneapp.data.model.responsemodel.LoginUserResponseModel
import com.dtech.done.doneapp.data.remote.callback.IGenericCallBack
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.utilities.extensions.hideAppLoader
import com.dtech.done.doneapp.utilities.extensions.onTokenExpiredLogout
import com.dtech.done.doneapp.utilities.extensions.showAppLoader
import com.dtech.done.doneapp.view.auth.LoginToYourAccountFragment
import com.dtech.done.doneapp.view.base.ActivityBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException


object SingleEnqueueCall {
    var snackbar = Snackbar.make(
        ActivityBase.activity.findViewById(android.R.id.content),
        Constants.CONST_NO_INTERNET_CONNECTION,
        Snackbar.LENGTH_INDEFINITE
    )


    fun <T> callRetrofit(
        call: Call<ApiResponse<T>>,
        apiName: String,
        isLoaderShown: Boolean,
        apiListener: IGenericCallBack
    ) {
        if (isLoaderShown)
            ActivityBase.activity.showAppLoader()
        snackbar.dismiss()
        call.enqueue(object : Callback<ApiResponse<T>> {
            override fun onResponse(
                call: Call<ApiResponse<T>>,
                response: Response<ApiResponse<T>>
            ) {
                hideAppLoader()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (!Gson().toJson(response.body()!!.data).equals("{}"))
                            apiListener.success(apiName, response.body()!!.data)
                        else {
                            apiListener.success(apiName, response.body()!!.message)
                        }
                        /* when(response.body()!!.code){
                             Constants.SUCCESS_CODE -> {
                                 if (!Gson().toJson(response.body()!!.data).equals("{}"))
                                     apiListener.success(apiName , response.body()!!.data)
                                 else{
                                     apiListener.success(apiName , response.body()!!.message)
                                 }
                             }
                             Constants.AUTHENTICATION_CODE -> {

                             }

                             Constants.TOKEN_EXPIRE -> {
                                 onTokenExpiredLogout()
                             }

                             else -> {
                                 apiListener.failure(apiName , response.body()!!.message)
                             }
                         }*/
                    }
                } else {
                    when {
                        response.code() == 401 -> {
                            ActivityBase.activity.onTokenExpiredLogout()
                            return
                        }
                        response.code() == 504 -> {
                            val callBack = this
                            enqueueWithRetry(call, callBack, isLoaderShown)
                        }


                        response.code() == Constants.TOKEN_EXPIRE -> {
                            val callRefresh = DoneApp.apiService.refreshToken()
                            val callBack = this
                            callRetrofitRefresh(
                                callRefresh,
                                Constants.REFRESH_TOKEN,
                                true,
                                object : IGenericCallBack {
                                    override fun success(apiName: String, response: Any?) {
                                        var reponse = response as LoginUserResponseModel
                                        DoneApp.db.putString(
                                            Constants.CONST_USER_TYPE,
                                            reponse!!.userType!!
                                        )
                                        DoneApp.db.putString(
                                            Constants.CONST_USER_DATA,
                                            Gson().toJson(reponse!!)
                                        )
                                        DoneApp.db.putBoolean(Constants.CONST_IS_LOGIN, true)
                                        DoneApp.db.putString(
                                            Constants.CONST_USER_PHONE,
                                            LoginToYourAccountFragment.phoneNumber
                                        )
                                        if (reponse.token != null) {
                                            DoneApp.db.putString(
                                                Constants.ACCESS_TOKEN,
                                                reponse.token!!.accessToken!!
                                            )
                                            DoneApp.db.putString(
                                                Constants.TOKEN_TYPE,
                                                reponse.token!!.tokenType!!
                                            )
                                        }

                                        enqueueWithRetry(call, callBack, isLoaderShown)
                                    }

                                    override fun failure(apiName: String, message: String?) {

                                    }
                                })
                        }
                        response.body() != null && response.body()!!.message != null -> {
                            apiListener.failure(apiName, response.body()!!.message)
                        }
                        response.errorBody() != null -> try {
                            var json = JSONObject(response.errorBody()!!.string())
                            if (json.has("message")) {
                                apiListener.failure(apiName, json.getString("message"))
                            } else {
                                apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                        }
                        else -> {
                            if (response.body() != null && response.body()!!.message != null)
                                apiListener.failure(apiName, response.body()!!.message)
                            else
                                apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            return
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
                hideAppLoader()
                val callBack = this
                if (t.message != "Canceled") {
                    if (t is UnknownHostException || t is IOException) {
                        Toast.makeText(
                            ActivityBase.activity,
                            Constants.CONST_NO_INTERNET_CONNECTION,
                            Toast.LENGTH_SHORT
                        ).show()
                        snackbar.setAction("Retry") {
                            snackbar.dismiss()
                            enqueueWithRetry(call, callBack, isLoaderShown)
                        }
                        snackbar.show()
                        apiListener.failure(apiName, Constants.CONST_NO_INTERNET_CONNECTION)
                    } else {
                        apiListener.failure(apiName, t.toString())
                    }
                }
            }
        })
    }

    fun <T> enqueueWithRetry(
        call: Call<ApiResponse<T>>,
        callback: Callback<ApiResponse<T>>,
        isLoaderShown: Boolean
    ) {
        ActivityBase.activity.showAppLoader()
        call.clone().enqueue(callback)
    }


    fun <T> callRetrofitRefresh(
        call: Call<ApiResponse<T>>,
        apiName: String,
        isLoaderShown: Boolean,
        apiListener: IGenericCallBack
    ) {
        if (isLoaderShown)
            ActivityBase.activity.showAppLoader()
        snackbar.dismiss()
        call.enqueue(object : Callback<ApiResponse<T>> {
            override fun onResponse(
                call: Call<ApiResponse<T>>,
                response: Response<ApiResponse<T>>
            ) {
                hideAppLoader()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (!Gson().toJson(response.body()!!.data).equals("{}")) {
                            apiListener.success(apiName, response.body()!!.data)

                        } else {
                            apiListener.success(apiName, response.body()!!.message)
                        }
                        /* when(response.body()!!.code){
                             Constants.SUCCESS_CODE -> {
                                 if (!Gson().toJson(response.body()!!.data).equals("{}"))
                                     apiListener.success(apiName , response.body()!!.data)
                                 else{
                                     apiListener.success(apiName , response.body()!!.message)
                                 }
                             }
                             Constants.AUTHENTICATION_CODE -> {

                             }

                             Constants.TOKEN_EXPIRE -> {
                                 onTokenExpiredLogout()
                             }

                             else -> {
                                 apiListener.failure(apiName , response.body()!!.message)
                             }
                         }*/
                    }
                } else {
                    when {
                        response.code() == 401 -> {
                            ActivityBase.activity.onTokenExpiredLogout()
                            return
                        }
                        response.code() == 504 -> {
                            val callBack = this
                            enqueueWithRetry(call, callBack, isLoaderShown)
                        }


                        response.code() == Constants.TOKEN_EXPIRE -> {

                        }
                        response.body() != null && response.body()!!.message != null -> {
                            apiListener.failure(apiName, response.body()!!.message)
                        }
                        response.errorBody() != null -> try {
                            var json = JSONObject(response.errorBody()!!.string())
                            if (json.has("message")) {
                                apiListener.failure(apiName, json.getString("message"))
                            } else {
                                apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                        }
                        else -> {
                            if (response.body() != null && response.body()!!.message != null)
                                apiListener.failure(apiName, response.body()!!.message)
                            else
                                apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            return
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
                hideAppLoader()
                val callBack = this
                if (t.message != "Canceled") {
                    if (t is UnknownHostException || t is IOException) {
                        Toast.makeText(
                            ActivityBase.activity,
                            Constants.CONST_NO_INTERNET_CONNECTION,
                            Toast.LENGTH_SHORT
                        ).show()
                        snackbar.setAction("Retry") {
                            snackbar.dismiss()
                            enqueueWithRetry(call, callBack, isLoaderShown)
                        }
                        snackbar.show()
                        apiListener.failure(apiName, Constants.CONST_NO_INTERNET_CONNECTION)
                    } else {
                        apiListener.failure(apiName, t.toString())
                    }
                }
            }
        })
    }

}
