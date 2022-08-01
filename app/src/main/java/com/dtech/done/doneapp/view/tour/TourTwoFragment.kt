package com.dtech.done.doneapp.view.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.FragmentTourOneBinding
import com.dtech.done.doneapp.databinding.FragmentTourTwoBinding
import com.dtech.done.doneapp.view.base.BaseFragment

class TourTwoFragment : BaseFragment() {

    lateinit var binding: FragmentTourTwoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tour_two, container, false)
        return binding.root
    }
}