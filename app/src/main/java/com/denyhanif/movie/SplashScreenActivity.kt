 package com.denyhanif.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.denyhanif.movie.onboarding.OnboardingOneActivity
import java.util.logging.Handler as Handler1

 class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var handler = Handler()// untuk menahan splashscreen activity
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity,// inten-> perpindahan ke activity lain(spalashcreen ke onboardingone)
                OnboardingOneActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)// waktu dealay 5 detik
    }
}


