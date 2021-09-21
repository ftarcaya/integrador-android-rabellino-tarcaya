package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.databinding.ActivityActivitiesBinding

class ActivitiesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesBinding

    private val listActivities = listOf<String>(
        "Education",
        "Recreational",
        "Social"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initRV()
    }

    private fun initRV() {
        binding.lvActivities.layoutManager = LinearLayoutManager(this)
        binding.lvActivities.adapter = ActivityAdapter(listActivities)
    }
}