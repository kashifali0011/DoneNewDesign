package com.dtech.done.doneapp.view.languageselection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.databinding.ActivityLanguageSelectionBinding
import com.dtech.done.doneapp.view.base.ActivityBase
import com.dtech.done.doneapp.view.tour.TourActivity

class LanguageSelectionActivity : ActivityBase(), View.OnClickListener {

    lateinit var binding: ActivityLanguageSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_selection)
        setListener()

    }

    private fun setListener(){
        binding.btnArabic.setOnClickListener(this)
        binding.btnEnglish.setOnClickListener(this)
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnArabic -> {
                binding.btnArabic.backgroundTintList = ContextCompat.getColorStateList(activity,R.color.blue)
                binding.btnArabic.setTextColor(resources.getColor(R.color.white))
                startActivity(Intent(this, TourActivity::class.java))
                finish()
            }

            R.id.btnEnglish -> {
                binding.btnEnglish.backgroundTintList = ContextCompat.getColorStateList(activity,R.color.blue)
                binding.btnEnglish.setTextColor(resources.getColor(R.color.white))
                startActivity(Intent(this, TourActivity::class.java))
                finish()
            }
        }
    }
}