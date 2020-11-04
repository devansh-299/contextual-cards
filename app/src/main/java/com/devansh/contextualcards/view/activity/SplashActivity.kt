package com.devansh.contextualcards.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Not inflating any layouts in order to avoid Cold Start delay
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}