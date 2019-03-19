package com.example.hammad.fifthkadesubmission.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hammad.fifthkadesubmission.R
import org.jetbrains.anko.startActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreenStyle);
        super.onCreate(savedInstanceState)
        //ConvertDate().convertTime("aa")
        startActivity<MainActivity>()
        finish()
    }
}
