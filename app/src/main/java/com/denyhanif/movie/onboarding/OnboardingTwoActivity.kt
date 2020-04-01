package com.denyhanif.movie.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denyhanif.movie.R
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_onboarding_one.btn_home
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)
        btn_home.setOnClickListener{
            finishAffinity()// hapus activity pada OnBoarding sebelumnya
            val intent= Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intent)
        }
    }
}
