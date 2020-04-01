package com.denyhanif.movie.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denyhanif.movie.R
import com.denyhanif.movie.sign.signin.SigninActivity
import com.denyhanif.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        //pref agar onboarding hanya muncul saat belum login
        preferences= Preferences(this)
        if (preferences.getValues("onboarging").equals("1")){
            finishAffinity()
            val intent= Intent( this@OnboardingOneActivity,SigninActivity::class.java)
            startActivity(intent)
        }


        //listeneter untuk button
        btn_daftar.setOnClickListener{
            val intent= Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }
        btn_home.setOnClickListener{
            finishAffinity()
            val intent= Intent(this@OnboardingOneActivity,
                SigninActivity::class.java)
            startActivity(intent)
        }
    }
}
