package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.databinding.ActivityMainBinding
import com.example.login.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)
    }
}