package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentTopUpBinding
import com.dtech.done.doneapp.utilities.extensions.hideKeyboard
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.bottomsheet.AddAddressBottomSheetFragment
import com.dtech.done.doneapp.view.bottomsheet.TopUpBottomSheetFragment
import com.dtech.done.doneapp.view.main.MainActivity

class TopUpFragment: BaseFragment(),View.OnClickListener , TextWatcher {
    lateinit var binding: FragmentTopUpBinding
    var selectPrice = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_top_up ,container , false)
        setListener()
        setToolbar()
       return binding.root
    }
    private fun setListener(){
        binding.tv200.setOnClickListener(this)
        binding.tv500.setOnClickListener(this)
        binding.tv700.setOnClickListener(this)
        binding.tv1000.setOnClickListener(this)
        binding.tv3000.setOnClickListener(this)
        binding.tv5000.setOnClickListener(this)
        binding.btnAddWallet.setOnClickListener(this)
        binding.edtEnterAmount.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv200 ->{
                setColor200()
            }
            R.id.tv500 ->{
                setColor500()
            }
            R.id.tv700 ->{
                setColor700()
            }
            R.id.tv1000 ->{
                setColor1000()
            }
            R.id.tv3000 ->{
                setColor3000()
            }
            R.id.tv5000 ->{
                setColor5000()
            }
            R.id.btnAddWallet ->{
                var balance = binding.edtEnterAmount.text.toString()
                if (balance.isNotEmpty()) {
                    showSaveAddress()
                }else{
                    Toast.makeText(activity,"select or add Price",Toast.LENGTH_SHORT).show()
                } } } }

    private fun setColor200(){
        selectPrice = "200"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.white))
        binding.tv500.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv700.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv1000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv3000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv5000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.non_fill_tab))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)

    }
    private fun setColor500(){
        selectPrice = "500"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv500.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.white))
        binding.tv700.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv1000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv3000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv5000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.non_fill_tab))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
    }
    private fun setColor700(){
        selectPrice = "700"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv500.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv700.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.white))
        binding.tv1000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv3000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv5000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.non_fill_tab))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
    }
    private fun setColor1000(){
        selectPrice = "1000"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv500.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv700.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv1000.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.white))
        binding.tv3000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv5000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.non_fill_tab))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
    }
    private fun setColor3000(){
        selectPrice = "3000"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv500.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv700.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv1000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv3000.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.white))
        binding.tv5000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.non_fill_tab))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
    }
    private fun setColor5000(){
        selectPrice = "5000"
        binding.edtEnterAmount.setText(selectPrice)
        binding.tv200.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv200.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv500.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv500.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv700.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv700.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv1000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv1000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv3000.setBackgroundResource(R.drawable.btn_white_rount_corner)
        binding.tv3000.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tv5000.setBackgroundResource(R.drawable.btn_rount_corner)
        binding.tv5000.setTextColor(resources.getColor(R.color.white))

        binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        var balance = binding.edtEnterAmount.text.toString()

        if (balance.isNotEmpty()){
            binding.btnAddWallet.setBackgroundResource(R.drawable.btn_rount_corner)
        }else if (balance.isEmpty()){
            binding.btnAddWallet.setBackgroundResource(R.drawable.btn_unselect_top_up_background)
        }
     /*   else if (balance == "200"){
           setColor200()
        }
        else if (balance == "500"){
            setColor500()
        } else if (balance == "700"){
            setColor700()
        }else if (balance == "1000"){
            setColor1000()
        }else if (balance == "3000"){
            setColor3000()
        }else if (balance == "500"){
            setColor5000()
        }
        else { }*/
    }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "My Wallet", null, ""))
    }
    private fun showSaveAddress() {

        var  topUpBottomSheet = TopUpBottomSheetFragment()
        if (!topUpBottomSheet.isAdded) {
            topUpBottomSheet.show(ActivityBase.activity.supportFragmentManager, "")
        }
    }

}