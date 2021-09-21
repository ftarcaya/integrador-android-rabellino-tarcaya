package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.etParticipants.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btStart.isEnabled = s?.isNotEmpty() ?: false && s.toString().toInt() > 0
            }
        })

        binding.btStart.setOnClickListener {
            val intent = Intent(this,ActivitiesActivity::class.java)
            intent.putExtra("PARTICIPANTS", binding.etParticipants.text)
            startActivity(intent)
        }

        binding.tvTerms.setOnClickListener {
            val intent = Intent(this,TermsActivity::class.java)
            startActivity(intent)
        }
    }
}