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

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivityBinding
    private lateinit var category : String
    private lateinit var participants : String
    private lateinit var price : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityBinding.inflate(layoutInflater)
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

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(APIService::class.java).getActivityByCategory(
                getUrlForRequest()
            )

            val activity : ActivityResponse? = call.body()

            runOnUiThread {
                if(call.isSuccessful) {

                    binding.tvActivity.text = activity?.name

                    binding.tvPriceCell2.text = activity?.let {
                        when(it.price.toFloat()) {
                            0F -> EnumActivity.FREE.description
                            in 0F..0.3F -> EnumActivity.LOW.description
                            in 0.3F..0.6F -> EnumActivity.MEDIUM.description
                            else -> EnumActivity.HIGH.description
                        }
                    }

                    binding.tvCategory.text = activity?.category
                } else {
                    Log.e("E","Error en call, Error Code ${call.code()}")
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
            //minaccessibility=:minaccessibility&maxaccessibility=:maxaccessibility
            if(participants.isNotEmpty() && price.isNotEmpty()){
                request = "participants=$participants&price=$price"
            }else if(participants.isEmpty()){
                request = "price=$price"
            }
        }
        return request
    }

    private fun getRetroFit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}