package com.example.fooding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooding.databinding.ActivitySplashBinding
import com.example.fooding.ui.signin.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkSignInStatus()
    }

    private fun checkSignInStatus() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val intent = if (account != null) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, SignInActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}