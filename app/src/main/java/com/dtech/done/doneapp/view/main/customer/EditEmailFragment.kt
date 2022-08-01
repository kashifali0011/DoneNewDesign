package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentEditEmailBinding
import com.dtech.done.doneapp.utilities.extensions.showToastMessage
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import java.util.regex.Pattern

class EditEmailFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentEditEmailBinding
    val emailString: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_email, container, false)
        setListener()
        setToolbar()
        return binding.root
    }
    fun setListener(){
        binding.btnVerifyEmail.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnVerifyEmail ->{
                if (validateInput(true)) {
                    addFragment(
                        R.id.mainContainer,
                        NotifyEmailVerificationFragment(),
                        "NotifyEmailVerificationFragment"
                    )
                }else{
                    requireActivity().showToastMessage("Please enter a valid mail")

                }
            }
        }
    }
    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "Edit Email", null, ""))
    }

    private fun validateInput(isFromButton : Boolean) : Boolean{
        if (isEmailValid(emailString.toString())){
            return true
        }
        return false
    }

}