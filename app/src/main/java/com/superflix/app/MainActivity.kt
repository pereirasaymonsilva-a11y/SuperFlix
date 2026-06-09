package com.superflix.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.superflix.app.ui.splash.SplashActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}