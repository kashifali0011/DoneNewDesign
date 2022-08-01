package com.dtech.done.doneapp.view.main.customer

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentMyOrdersBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.AcceptedOrdersListAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.CompletedOrdersListAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.FailedOrdersListAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.PendingOrdersAdapter
import com.sangcomz.fishbun.BaseActivity
import kotlinx.android.synthetic.main.adapter_accepted_order_list.view.*


class MyOrdersFragment:BaseFragment(), View.OnClickListener {
    lateinit var binding:FragmentMyOrdersBinding
    private var adapterPendingOrders: PendingOrdersAdapter? = null
    private var acceptedOrdersListAdapter: AcceptedOrdersListAdapter? = null
    private var completedOrdersListAdapter:CompletedOrdersListAdapter? = null
    private var failedOrdersListAdapter: FailedOrdersListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_orders, container, false)
        setToolbar()
        setListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvForPendingOrders()
        setVisibilityOnPending()
        setTextFontColorOnPending()
    }
    private fun setListener() {
        binding.llPending.setOnClickListener(this)
        binding.llAccepted.setOnClickListener(this)
        binding.llCompleted.setOnClickListener(this)
        binding.llFailed.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llPending -> {
                setTextFontColorOnPending()
                setVisibilityOnPending()
                setRvForPendingOrders()
            }
            R.id.llAccepted -> {
                setTextFontColorOnAccepted()
                setVisibilityOnAccepted()
                setRvForAcceptedOrders()
            }
            R.id.llCompleted -> {
                setTextFontColorOnCompleted()
                setVisibilityOnCompleted()
                setRvForCompletedOrders()
            }
            R.id.llFailed -> {
                setTextFontColorOnFailed()
                setVisibilityOnFailed()
                setRvForFailedOrders()
            }
        }
    }

    private fun setRvForPendingOrders(){

        binding.rvOrdersDetail.apply {
            layoutManager = LinearLayoutManager(ActivityBase.activity)
            adapterPendingOrders = PendingOrdersAdapter(ActivityBase.activity)
            binding.rvOrdersDetail.adapter = adapterPendingOrders
            adapterPendingOrders!!.notifyDataSetChanged()


        }
    }
    private fun setVisibilityOnPending(){
        binding.ivPendingBottomLine.visibility = View.VISIBLE
        binding.ivAcceptedBottomLine.visibility = View.GONE
        binding.ivCompletedBottomLine.visibility = View.GONE
        binding.ivFailedBottomLine.visibility = View.GONE
    }
    private fun setTextFontColorOnPending(){
        binding.tvPending.setCustomFont(requireActivity(),"poppins_bold.ttf")
        binding.tvPending.setTextColor(resources.getColor(R.color.blue_text_color))
        binding.tvAccepted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvAccepted.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvCompleted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvCompleted.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvFailed.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvFailed.setTextColor(resources.getColor(R.color.non_fill_tab))
    }
    private fun setRvForAcceptedOrders(){
        binding.rvOrdersDetail.apply {
            layoutManager = LinearLayoutManager(ActivityBase.activity)
            acceptedOrdersListAdapter = AcceptedOrdersListAdapter(ActivityBase.activity)
            binding.rvOrdersDetail.adapter = acceptedOrdersListAdapter
            acceptedOrdersListAdapter!!.notifyDataSetChanged()
        }
    }
    private fun setVisibilityOnAccepted(){
        binding.ivPendingBottomLine.visibility = View.GONE
        binding.ivAcceptedBottomLine.visibility = View.VISIBLE
        binding.ivCompletedBottomLine.visibility = View.GONE
        binding.ivFailedBottomLine.visibility = View.GONE
    }
    private fun setTextFontColorOnAccepted(){
        binding.tvAccepted.setCustomFont(requireActivity(),"poppins_bold.ttf")
        binding.tvAccepted.setTextColor(resources.getColor(R.color.blue_text_color))
        binding.tvPending.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvPending.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvCompleted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvCompleted.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvFailed.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvFailed.setTextColor(resources.getColor(R.color.non_fill_tab))
    }
    private fun setRvForCompletedOrders(){
        binding.rvOrdersDetail.apply {
            layoutManager = LinearLayoutManager(ActivityBase.activity)
            completedOrdersListAdapter = CompletedOrdersListAdapter(ActivityBase.activity)
            binding.rvOrdersDetail.adapter = completedOrdersListAdapter
            completedOrdersListAdapter!!.notifyDataSetChanged()
        }
    }
    private fun setVisibilityOnCompleted(){
        binding.ivPendingBottomLine.visibility = View.GONE
        binding.ivAcceptedBottomLine.visibility = View.GONE
        binding.ivCompletedBottomLine.visibility = View.VISIBLE
        binding.ivFailedBottomLine.visibility = View.GONE
    }
    private fun setTextFontColorOnCompleted(){
        binding.tvCompleted.setCustomFont(requireActivity(),"poppins_bold.ttf")
        binding.tvCompleted.setTextColor(resources.getColor(R.color.blue_text_color))
        binding.tvAccepted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvAccepted.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvPending.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvPending.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvFailed.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvFailed.setTextColor(resources.getColor(R.color.non_fill_tab))
    }
    private fun setRvForFailedOrders(){
        binding.rvOrdersDetail.apply {
            layoutManager = LinearLayoutManager(ActivityBase.activity)
            failedOrdersListAdapter = FailedOrdersListAdapter(ActivityBase.activity)
            binding.rvOrdersDetail.adapter = failedOrdersListAdapter
            failedOrdersListAdapter!!.notifyDataSetChanged()
        }
    }
    private fun setVisibilityOnFailed(){
        binding.ivPendingBottomLine.visibility = View.GONE
        binding.ivCompletedBottomLine.visibility = View.GONE
        binding.ivAcceptedBottomLine.visibility = View.GONE
        binding.ivFailedBottomLine.visibility = View.VISIBLE
    }
    private fun setTextFontColorOnFailed(){
        binding.tvFailed.setCustomFont(requireActivity(),"poppins_bold.ttf")
        binding.tvFailed.setTextColor(resources.getColor(R.color.blue_text_color))
        binding.tvAccepted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvAccepted.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvPending.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvPending.setTextColor(resources.getColor(R.color.non_fill_tab))
        binding.tvCompleted.setCustomFont(requireActivity(),"poppins_regular.ttf")
        binding.tvCompleted.setTextColor(resources.getColor(R.color.non_fill_tab))
    }
    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "My Orders", null, ""))
    }
}