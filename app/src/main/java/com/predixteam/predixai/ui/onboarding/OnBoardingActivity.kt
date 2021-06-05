package com.predixteam.predixai.ui.onboarding


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.predixteam.predixai.MainActivity
import com.predixteam.predixai.R

class OnBoardingActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!
        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.primaryDark),
            unselectedIndicatorColor = getColor(R.color.primaryColor)
        )
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.first_onboarding))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.second_onboarding))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.third_onboarding))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        onBoardFinished()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        onBoardFinished()
        finish()
    }

    private fun onBoardFinished(){
        startActivity(Intent(this, MainActivity::class.java))
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}