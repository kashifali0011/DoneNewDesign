package com.dtech.done.doneapp.view.main.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.custommodel.SaveWalletLayoutModel
import com.dtech.done.doneapp.data.model.custommodel.SetPositionCategoryModel
import com.dtech.done.doneapp.data.model.custommodel.ToolbarModel
import com.dtech.done.doneapp.databinding.FragmentWalletBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity
import com.dtech.done.doneapp.view.main.customer.adapter.CategoryAdapter
import com.dtech.done.doneapp.view.main.customer.adapter.WalletAdapter

class WalletFragment: BaseFragment() , View.OnClickListener,WalletAdapter.OnItemClickListener{

    lateinit var binding: FragmentWalletBinding
    var walletAdapter: WalletAdapter? = null
    val arrayList: ArrayList<SaveWalletLayoutModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_wallet, container,false)
        setListener()
        getWallet()
        setToolbar()
        return binding.root
    }

    private fun getWallet(){
        arrayList.add(SaveWalletLayoutModel(0))
        arrayList.add(SaveWalletLayoutModel(0))
        arrayList.add(SaveWalletLayoutModel(0))
        arrayList.add(SaveWalletLayoutModel(0))
        val categoryLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        binding.rvWallet.layoutManager = categoryLayoutManager
        walletAdapter = WalletAdapter(requireActivity(),arrayList,this)
        binding.rvWallet.adapter = walletAdapter
    }

    private fun setListener(){
        binding.tvViewAll.setOnClickListener(this)
        binding.llTopUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.tvViewAll ->{
               binding.tvTransactions.isVisible = false
               binding.tvViewAll.isVisible = false
               arrayList.clear()
               arrayList.add(SaveWalletLayoutModel(1))
               arrayList.add(SaveWalletLayoutModel(1))
               arrayList.add(SaveWalletLayoutModel(1))
               arrayList.add(SaveWalletLayoutModel(1))
               arrayList.add(SaveWalletLayoutModel(1))
               arrayList.add(SaveWalletLayoutModel(1))
               walletAdapter!!.notifyDataSetChanged()
           }
           R.id.llTopUp ->{
               addFragment(R.id.mainContainer , TopUpFragment() , "TopUpFragment")
           }
       }
    }

    fun setToolbar(){
        (ActivityBase.activity as MainActivity).setToolbar(ToolbarModel(false, true, false, false, false, "", "My Wallet", null, ""))
    }

    override fun onItemClick(position: Int) {
    }
}