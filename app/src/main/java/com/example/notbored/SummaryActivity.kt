package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.notbored.databinding.ActivitySummaryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding
    private lateinit var category : String
    private lateinit var participants : String
    private lateinit var price : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        category = intent.extras?.get("ACTIVITY").toString()
        participants = intent.extras?.get("PARTICIPANTS").toString()
        price = intent.extras?.get("PRICE").toString()

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

        binding.btTryAnother.isEnabled = false
        binding.tlActivityTable.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
        binding.tvActivity.text = "Loading..."

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(APIService::class.java).getActivityByCategory(
                getUrlForRequest()
            )

            Log.i("I/O", "PASE POR ACA")

            val activity : ActivityResponse? = call.body()

            Log.i("O/I","Body $activity")

            runOnUiThread {
                if(call.isSuccessful) {

                    activity?.error?.let {
                        Toast.makeText(applicationContext, activity.error, Toast.LENGTH_SHORT)
                            .show()
                        Log.e("E", activity.error)
                        finish()
                    } ?: run {
                        binding.tvParticipantsCell2.text = activity?.participants

                        binding.tvActivity.text = activity?.name

                        binding.tvPriceCell2.text = activity?.let {
                            when(it.price.toFloat()) {
                                0F -> EnumActivity.FREE.description
                                in 0F..0.3F -> EnumActivity.LOW.description
                                in 0.3F..0.6F -> EnumActivity.MEDIUM.description
                                else -> EnumActivity.HIGH.description
                            }
                        }

                        binding.tvCategory.text = activity?.category?.capitalize()

                        binding.btTryAnother.isEnabled = true
                        binding.tlActivityTable.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(applicationContext, "Connection Error!", Toast.LENGTH_SHORT).show()
                    Log.e("E","Error en call, Error Code ${call.code()}")
                    finish()
                }
            }
        }
    }

    private fun getUrlForRequest(): String {
        var request = "activity"
        var isNested = false
        if(category == "RANDOM" && participants.isEmpty() && price.isEmpty()){
            request = "$request/"
        } else {
            request = "$request?"

            if(category != "RANDOM") {
                isNested = true
                request = "${request}type=${category.lowercase()}"
            }

            if (participants.isNotEmpty()) {
                isNested = true
                request += if (isNested) "&participants=$participants" else "participants=$participants"
            }

            if (price.isNotEmpty()) {
                request += if (isNested) "&price=$price" else "price=$price"
            }
        }
        Log.i("I","Request ${request}")

        return request
    }

    private fun getRetroFit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}