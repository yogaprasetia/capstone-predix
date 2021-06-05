package com.predixteam.predixai.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.predixteam.predixai.MainActivity
import com.predixteam.predixai.databinding.ActivitySplashBinding
import com.predixteam.predixai.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            if (onBoardingFinished()){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            finish()
        }, MILLIS)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    companion object{
        private const val MILLIS = 3000L
    }

}