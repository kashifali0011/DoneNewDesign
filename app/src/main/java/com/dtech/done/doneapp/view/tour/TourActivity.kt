package com.dtech.done.doneapp.view.tour

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.ActivityTourBinding
import com.dtech.done.doneapp.utilities.helper.VPDashBoardAdapter
import com.dtech.done.doneapp.view.base.ActivityBase

class TourActivity : ActivityBase(), ViewPager.OnPageChangeListener {

    lateinit var binding : ActivityTourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tour)
        setListeners()
        setupPager()

    }

    private fun setListeners() {
        binding.vpTour.addOnPageChangeListener(this)
    }

    private fun setupPager() {
        val adapter = VPDashBoardAdapter(supportFragmentManager)
        adapter.addFragment(TourOneFragment(), "TourPage1Fragment")
        adapter.addFragment(TourTwoFragment(), "TourPage2Fragment")
        adapter.addFragment(TourThreeFragment(), "TourPage3Fragment")
        binding.vpTour.adapter = adapter
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                binding.tab1.setImageResource(R.drawable.ic_circle_fill_tab)
                binding.tab2.setImageResource(R.drawable.ic_circle_non_fill)
                binding.tab3.setImageResource(R.drawable.ic_circle_non_fill)
            }
            1 -> {

                binding.tab1.setImageResource(R.drawable.ic_circle_non_fill)
                binding.tab2.setImageResource(R.drawable.ic_circle_fill_tab)
                binding.tab3.setImageResource(R.drawable.ic_circle_non_fill)
            }

            2 -> {

                binding.tab1.setImageResource(R.drawable.ic_circle_non_fill)
                binding.tab2.setImageResource(R.drawable.ic_circle_non_fill)
                binding.tab3.setImageResource(R.drawable.ic_circle_fill_tab)
            }

        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}