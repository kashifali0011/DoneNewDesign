package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveCouponApplyModel
import com.dtech.done.doneapp.data.model.custommodel.SaveCouponModel
import com.dtech.done.doneapp.data.model.custommodel.SetPositionCategoryModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentMyCartBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.CouponAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.CouponApplyAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.MyCartAdapter
import kotlinx.coroutines.delay
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MyCartFragment : BaseFragment(), View.OnClickListener, CouponAdapter.OnItemClickListener {
    lateinit var binding: FragmentMyCartBinding
    var myCartAdapter: MyCartAdapter? = null
    var couponList: ArrayList<SaveCouponModel> = ArrayList()
    var couponApplyList: ArrayList<SaveCouponApplyModel> = ArrayList()
    var couponAdapter: CouponAdapter? = null
    var couponApplyAdapter:CouponApplyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart, container, false)
        setListener()
        setToolbar()
        getMyServices()
        return binding.root
    }

    private fun getMyServices() {

        val bannerLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvMyServices.layoutManager = bannerLayoutManager
        myCartAdapter = MyCartAdapter(requireActivity())
        binding.rvMyServices.adapter = myCartAdapter

    }

    private fun setListener() {
        binding.btnCheckOut.setOnClickListener(this)
        binding.cvAddCoupon.setOnClickListener(this)
        binding.cvApplyCoupon.setOnClickListener(this)
        binding.edtAddCoupon.addTextChangedListener(textWatcher)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCheckOut -> {
                addFragment(R.id.mainContainer, MyCartPreviewFragment(), "MyCartPreviewFragment")
            }
            R.id.cvAddCoupon -> {

                var textLenght = binding.edtAddCoupon.text.toString()
                if (textLenght.isNotEmpty()) {
                    couponList.add(SaveCouponModel(binding.edtAddCoupon.text.toString()))
                    couponApplyList.add(SaveCouponApplyModel(binding.edtAddCoupon.text.toString()))
                    binding.edtAddCoupon.setText("")
                    addCoupon()
                }
            }
            R.id.cvApplyCoupon ->{
                applyCoupon()
            }

        }
    }

    fun setToolbar() {
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "My Cart", null, ""))
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            var textLenght = binding.edtAddCoupon.text.toString()

            if (textLenght.isNotEmpty()) {
                binding.cvAddCoupon.isVisible = true
                binding.cvApplyCoupon.isVisible = false
            }
            if (textLenght.isEmpty()) {
                binding.cvAddCoupon.isVisible = false
                binding.cvApplyCoupon.isVisible = true

            }
        }
    }

    private fun addCoupon() {
        val categoryLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCoupon.layoutManager = categoryLayoutManager
        couponAdapter = CouponAdapter(requireActivity(), couponList, this)
        binding.rvCoupon.adapter = couponAdapter

    }

    private fun applyCoupon() {
        val categoryLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCouponApply.layoutManager = categoryLayoutManager
        couponApplyAdapter = CouponApplyAdapter(requireActivity(), couponApplyList)
        binding.rvCouponApply.adapter = couponApplyAdapter

      /*  Timer().schedule(10){
            ActivityBase.activity.runOnUiThread { */
                couponList.clear()
                addCoupon()
        /*    }

        }*/

    }

    override fun onItemClick(position: Int) {
        couponList.removeAt(position)
        addCoupon()
    }
}