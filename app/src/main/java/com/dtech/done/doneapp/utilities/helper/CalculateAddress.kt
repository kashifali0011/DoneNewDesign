package com.dtech.done.doneapp.utilities.helper

import android.os.AsyncTask
import com.dtech.done.doneapp.utilities.extensions.getAddressFromLatLng
import com.dtech.done.doneapp.view.base.ActivityBase

class CalculateAddress(val listener: IAddressListener) : AsyncTask<Double, Double, String>() {
    override fun doInBackground(vararg params: Double?): String? {
        return ActivityBase.activity.getAddressFromLatLng(params[0]!!, params[1]!!)
    }

    override fun onPreExecute() {
        listener.showLoading()
    }

    override fun onPostExecute(result: String?) {
        listener.endLoading()
        listener.addressCalculated(result)
    }

    interface IAddressListener {
        fun showLoading()
        fun endLoading()
        fun addressCalculated(result: String?)
    }
}