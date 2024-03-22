package com.company.health_app.difault

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.health_app.MainActivity
import com.company.health_app.databinding.ActivityDefaultBinding
import com.google.android.material.tabs.TabLayoutMediator

class DefaultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefaultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = DefaultAdapter(this)

        val result = intent.getBooleanExtra("endDefaultFragment" , false)

        TabLayoutMediator(binding.tabLayout , binding.viewPager) {tab , position ->
            run {
                tab.text = when(position) {
                    0 -> "가슴"
                    1 -> "어깨"
                    2 -> "등"
                    3 -> "팔"
                    4 -> "하체"
                    else -> null
                }
            }
        }.attach()
        if(result) {
            callMainActivityToNotifyEndDefaultActivity()
        }
    }

    private fun callMainActivityToNotifyEndDefaultActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("endDefaultFragment" , true)
        startActivity(intent)
    }

}