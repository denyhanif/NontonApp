package com.denyhanif.movie.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denyhanif.movie.R
import com.denyhanif.movie.sign.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_onboarding_one.btn_home

class OnboardingThreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)
        btn_home.setOnClickListener{
            finishAffinity()// hapus activity pada OnBoarding sebelumnya
            val intent= Intent(this@OnboardingThreeActivity, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}
