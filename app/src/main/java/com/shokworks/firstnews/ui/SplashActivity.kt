package com.shokworks.firstnews.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shokworks.firstnews.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, NavigationActivity::class.java)).also { finish() }
            }
        }
    }
}