package com.example.notbored

import com.example.notbored.Adapters.ActivityAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.databinding.ActivitySelectorActivitiesBinding

class ActivitiesSelectorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorActivitiesBinding
    private lateinit var participants : String
    private lateinit var price : String

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
        binding = ActivitySelectorActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        participants = intent.extras?.get("PARTICIPANTS").toString()
        price = intent.extras?.get("PRICE").toString()

        binding.ivRandom.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("ACTIVITY","RANDOM")
            intent.putExtra("PARTICIPANTS",participants)
            intent.putExtra("PRICE",price)
            startActivity(intent)
        }
        initRV()
    }

    private fun initRV() {
        binding.lvActivities.layoutManager = LinearLayoutManager(this)
        binding.lvActivities.adapter = ActivityAdapter(listActivities,participants,price)
    }
}