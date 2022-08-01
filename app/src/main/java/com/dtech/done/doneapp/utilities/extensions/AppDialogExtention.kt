package com.dtech.done.doneapp.utilities.extensions

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.R.color
import com.dtech.done.doneapp.R.color.*
import com.dtech.done.doneapp.utilities.helper.TypeFaceButton
import com.dtech.done.doneapp.utilities.helper.TypeFaceRecyclerView
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.main.customer.NotificationFragment
import com.dtech.done.doneapp.view.main.customer.adapter.RejectProviderOptionsAdapter
import com.google.android.gms.dynamic.SupportFragmentWrapper
import kotlinx.android.synthetic.main.fragment_top_up.view.*


/**
 * Develop By Messagemuse
 */
var loadingDialoge: Dialog? = null

fun Context.showAppLoader() {
    if (loadingDialoge != null) {
        if (!loadingDialoge!!.isShowing) {
            loadingDialoge = Dialog(this, R.style.Theme_Dialog_Loader)
            loadingDialoge!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loadingDialoge!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialoge!!.setContentView(R.layout.dialoge_app_loader)
            loadingDialoge!!.setCancelable(false)
            loadingDialoge!!.show()
        }
    } else {
        loadingDialoge = Dialog(this, R.style.Theme_Dialog_Loader)
        loadingDialoge!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialoge!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialoge!!.setContentView(R.layout.dialoge_app_loader)
        loadingDialoge!!.setCancelable(false)
        loadingDialoge!!.show()
    }
}

fun hideAppLoader() {
    if (loadingDialoge != null && loadingDialoge!!.isShowing) {
        try {
            loadingDialoge!!.dismiss()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}

fun Context.confirmDialogBox(title: String , message: String ) {
    val confirmDialog = Dialog(this, R.style.Theme_Dialog)
    confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    confirmDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    confirmDialog.setCancelable(true)
    confirmDialog.setContentView(R.layout.confirm_layout)
    val btnCancel = confirmDialog.findViewById<Button>(R.id.btnCancel)
    val btnLogOut = confirmDialog.findViewById<Button>(R.id.btnLogOut)
    val tvDescription = confirmDialog.findViewById<TextView>(R.id.tvDescription)
    val tvTitle = confirmDialog.findViewById<TextView>(R.id.tvTitle)
    tvDescription.text = message
    tvTitle.text = title
    btnCancel.setOnClickListener {
        confirmDialog.dismiss()
    }
    btnLogOut.setOnClickListener {
        ActivityBase.activity.onTokenExpiredLogout()
        confirmDialog.dismiss()
    }

    confirmDialog.show()
}



fun Context.webViewDialogBox(url: String) {
    val wevViewDialog = Dialog(this, R.style.Theme_Dialog)
    wevViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    wevViewDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    wevViewDialog.setCancelable(true)
    wevViewDialog.setContentView(R.layout.app_webview)
    val ivClose = wevViewDialog.findViewById<ImageView>(R.id.ivClose)
    val webView = wevViewDialog.findViewById<WebView>(R.id.webView)
    ivClose.setOnClickListener {
        wevViewDialog.dismiss()
    }
    webView.settings.setJavaScriptEnabled(true)
    webView.settings.setAppCacheEnabled(false)
    webView.loadUrl(url)
    wevViewDialog.show()
}

fun Context.deleteAccountDialogBox() {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_white_corner_dialog)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.dialog_delete_account)
    val btnDeleteAccount = dialog.findViewById<TypeFaceButton>(R.id.btnDeleteAccount)
    val btnGoBack = dialog.findViewById<TypeFaceButton>(R.id.btnGoBack)
    val et1 = dialog.findViewById<EditText>(R.id.et1)
    val et2 = dialog.findViewById<EditText>(R.id.et2)
    val et3 = dialog.findViewById<EditText>(R.id.et3)
    val et4 = dialog.findViewById<EditText>(R.id.et4)
    btnDeleteAccount.setOnClickListener {
        dialog.dismiss()
    }
    btnGoBack.setOnClickListener {
        dialog.dismiss()
    }
    et1.addTextChangedListener {
        if (et1.hasFocus()) {
            if (et1.text.toString().length == 1) {
                btnDeleteAccount.background = ContextCompat.getDrawable(
                    ActivityBase.activity,
                    R.drawable.bg_orange_btn_delete_account
                )
                et1.clearFocus()
                et2.requestFocus()
            }
        }
    }
    et2.addTextChangedListener {
        if (et2.hasFocus()) {
            if (et2.text.toString().length == 1) {
                btnDeleteAccount.background = ContextCompat.getDrawable(
                    ActivityBase.activity,
                    R.drawable.bg_orange_btn_delete_account
                )
                et2.clearFocus()
                et3.requestFocus()
            }
        }
    }
    et3.addTextChangedListener {
        if (et3.hasFocus()) {
            if (et3.text.toString().length == 1) {
                btnDeleteAccount.background = ContextCompat.getDrawable(
                    ActivityBase.activity,
                    R.drawable.bg_orange_btn_delete_account
                )
                et3.clearFocus()
                et4.requestFocus()
            }
        }
    }
    et4.addTextChangedListener {
        if (et4.hasFocus()) {
            if (et4.text.toString().length == 1) {
                btnDeleteAccount.background = ContextCompat.getDrawable(
                    ActivityBase.activity,
                    R.drawable.bg_orange_btn_delete_account
                )
                et1.clearFocus()
                et2.clearFocus()
                et3.clearFocus()
            }
        }
    }
    et1.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
                et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
                et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)

        }
    }


    et2.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
            et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
        }
    }
    et3.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
            et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
        }
    }
    et4.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            et1.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et2.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et3.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_disable)
            et4.background = ContextCompat.getDrawable(ActivityBase.activity, R.drawable.bg_et_pin)
        }
    }
    et1.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            //Perform Code
        }
        false
    })
    et2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (TextUtils.isEmpty(et2.text.toString())) {
                    et2.clearFocus()
                    et1.requestFocus()
                } else {
                    et2.setText("")
                }
        }
        false
    })
    et3.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (TextUtils.isEmpty(et3.text.toString())) {
                et3.clearFocus()
                et2.requestFocus()
            } else {
                et3.setText("")
            }
        }
        false
    })
    et4.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (TextUtils.isEmpty(et4.text.toString())) {
                et4.clearFocus()
                et3.requestFocus()
            } else {
                et4.setText("")
            }
        }
        false
    })
    dialog.show()
}

fun Context.ratingDialog() {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_white_corner_dialog)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.dialog_rating_account)
    var btnSubmit = dialog.findViewById<TypeFaceButton>(R.id.btnSubmit)
    val ratingBar = dialog.findViewById<RatingBar>(R.id.myRatingBar)
      if(ratingBar.hasFocus()){
          btnSubmit!!.setBackgroundResource(R.drawable.bg_btn_go_back)
      }
    btnSubmit.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}
@SuppressLint("ResourceAsColor")
fun Context.rejectProviderDialog(mListner: rejectDialogClickListner) {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_white_corner_dialog)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.dialog_reject_provider)
    val btnCancel = dialog.findViewById<TypeFaceButton>(R.id.btnCancel)
    val btnYes = dialog.findViewById<TypeFaceButton>(R.id.btnYes)
    val btnClose = dialog.findViewById<ImageView>(R.id.ivClose)
    val rvOptions = dialog.findViewById<TypeFaceRecyclerView>(R.id.rvOptions)
    rvOptions.apply {
        layoutManager = LinearLayoutManager(ActivityBase.activity)
        var rejectProviderOptionsAdapter:RejectProviderOptionsAdapter =
            RejectProviderOptionsAdapter(ActivityBase.activity,object :
            RejectProviderOptionsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
            }
        })
        rvOptions.adapter = rejectProviderOptionsAdapter
        rejectProviderOptionsAdapter!!.notifyDataSetChanged()
    }
    btnCancel.setOnClickListener {
        dialog.dismiss()
    }
    btnClose.setOnClickListener {
        dialog.dismiss()
    }
    btnYes.setOnClickListener {
        btnYes.setTextColor(blue_text_color)
        btnYes!!.setBackgroundResource(R.drawable.btn_blue_corner)
        mListner.yesBtn()
        dialog.dismiss()
    }

    dialog.show()

}

interface rejectDialogClickListner {
    fun yesBtn()
}



