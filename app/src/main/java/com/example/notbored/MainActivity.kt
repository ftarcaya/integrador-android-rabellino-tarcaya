package com.example.notbored

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences("ACTIVITY", Context.MODE_PRIVATE)

        binding.cbTerms.isChecked = sharedPref.getBoolean("TERMS",false)

        validateFields()

        binding.etParticipants.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFields()
            }
        })

        binding.etPriceSelector.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFields()
            }
        })

        binding.cbTerms.setOnClickListener {
            validateFields()
        }

        binding.btStart.setOnClickListener {
            val editor = sharedPref.edit()

            editor.putBoolean("TERMS",true)

            editor.apply()

            val intent = Intent(this,ActivitiesSelectorActivity::class.java)
            intent.putExtra("PARTICIPANTS", binding.etParticipants.text)
            intent.putExtra("PRICE", binding.etPriceSelector.text)
            startActivity(intent)
        }

        binding.tvTerms.setOnClickListener {
            val intent = Intent(this,TermsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateFields() {
        val participantsCondition = (binding.etParticipants.text?.isEmpty() ?: true || binding.etParticipants.text.toString().toInt() != 0)
        val priceCondition = (binding.etPriceSelector.text?.isEmpty() ?: true || binding.etPriceSelector.text.toString().toFloat() in 0F..1F)
        val termsCondition = binding.cbTerms.isChecked
        if (!participantsCondition) {
            runOnUiThread {
                binding.etParticipants.setError("You need at least 1 participant or leave it empty")
            }
        }
        if (!priceCondition) {
            runOnUiThread {
                binding.etPriceSelector.setError("The price must be between 0 and 1 or empty")
            }
        }
        binding.btStart.isEnabled = participantsCondition and priceCondition and termsCondition
    }
}