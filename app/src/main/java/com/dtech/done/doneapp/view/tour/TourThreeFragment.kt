package com.dtech.done.doneapp.view.tour

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.DoneApp
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.FragmentTourThreeBinding
import com.dtech.done.doneapp.databinding.FragmentTourTwoBinding
import com.dtech.done.doneapp.utilities.Constants
import com.dtech.done.doneapp.view.auth.AuthActivity
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.base.BaseFragment
import com.dtech.done.doneapp.view.main.MainActivity

class TourThreeFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentTourThreeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tour_three, container, false)
        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.btnGetStarted.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnGetStarted -> {
                DoneApp.db.putBoolean(Constants.CONST_IS_TOUR , true)
                startActivity(Intent(ActivityBase.activity , AuthActivity::class.java))
                ActivityBase.activity.finish()
            }
        }
    }
}