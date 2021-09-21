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
    private lateinit var participants : String

    private val listActivities = listOf<String>(
        "Education",
        "Recreational",
        "Social",
        "Diy",
        "Charity",
        "Cooking",
        "Relaxation",
        "Music",
        "Busywork"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        participants = intent.extras?.get("PARTICIPANTS").toString()

        binding.ivRandom.setOnClickListener {
            val intent = Intent(this, ActivityActivity::class.java)
            intent.putExtra("ACTIVITY","RANDOM")
            intent.putExtra("PARTICIPANTS",participants)
            startActivity(intent)
        }

        initRV()
    }

    private fun initRV() {
        binding.lvActivities.layoutManager = LinearLayoutManager(this)
        binding.lvActivities.adapter = ActivityAdapter(listActivities,participants)
    }
}