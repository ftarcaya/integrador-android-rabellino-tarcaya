package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notbored.databinding.ActivityActivityBinding
import com.example.notbored.databinding.ActivityMainBinding

class ActivityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Toast.makeText(this, intent.extras?.get("ACTIVITY").toString(), Toast.LENGTH_SHORT).show()

    }
}