package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.notbored.databinding.ActivityActivityBinding
import com.example.notbored.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivityBinding
    private lateinit var category : String
    private lateinit var participants : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        category = intent.extras?.get("ACTIVITY").toString()
        participants = intent.extras?.get("PARTICIPANTS").toString()
        binding.tvParticipantsCell2.text = participants

        checkCategory()

        searchActivityByCategory()

        binding.btTryAnother.setOnClickListener {
            searchActivityByCategory()
        }

        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun checkCategory() {
        binding.trCategory.visibility = if(category == "RANDOM") View.VISIBLE else View.GONE
    }

    private fun searchActivityByCategory() {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(APIService::class.java).getActivityByCategory(
                if(category == "RANDOM") "activity/" else "activity?type=${category.lowercase()}"
            )

            val activity : ActivityResponse? = call.body()

            runOnUiThread {
                if(call.isSuccessful) {

                    binding.tvActivity.text = activity?.name

                    //TODO WHEN
                    binding.tvPriceCell2.text = activity?.price

                    binding.tvCategory.text = activity?.category
                } else {
                    Log.e("E","Error en call, Error Code ${call.code()}")
                }
            }
        }
    }

    private fun getRetroFit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}