package com.dtech.done.doneapp.data.remote


import com.dtech.done.doneapp.BuildConfig
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.utilities.Constants
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val baseUrl = Constants.BASE_URL
    private var retrofit: Retrofit? = null
    val dispatcher = Dispatcher()

    fun getClient(): Retrofit? {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.level = HttpLoggingInterceptor.Level.BODY
        else
            logging.level = HttpLoggingInterceptor.Level.NONE
        if (retrofit == null) {
            retrofit = Retrofit.Builder().client(OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(false).dispatcher(
                    dispatcher
                ).addInterceptor(Interceptor { chain: Interceptor.Chain? ->
                val newRequest = chain?.request()!!.newBuilder()
                    newRequest.addHeader("Connection","close")
                    newRequest.addHeader("device-type" , "Android")
                    newRequest.addHeader("app-version" , "1.5")
                    newRequest.addHeader("lang" , "en")
                    newRequest.addHeader("Accept" , "application/json" )
                    newRequest.addHeader("Authorization", "${DoneApp.db.getString(Constants.TOKEN_TYPE)} ${DoneApp.db.getString(Constants.ACCESS_TOKEN)}")
                return@Interceptor chain.proceed(newRequest.build())
            }).addInterceptor(logging).build())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofit
    }


}