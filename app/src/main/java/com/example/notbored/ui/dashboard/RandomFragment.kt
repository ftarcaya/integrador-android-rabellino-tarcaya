package com.example.notbored.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notbored.APIService
import com.example.notbored.ActivityResponse
import com.example.notbored.EnumActivity
import com.example.notbored.databinding.FragmentRandomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomFragment : Fragment() {

    private lateinit var dashboardViewModel: RandomViewModel
    private var _binding: FragmentRandomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(RandomViewModel::class.java)

        _binding = FragmentRandomBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRandomActivity()
        binding.btTryAnother.setOnClickListener {
            getRandomActivity()
        }
    }

    private fun getRandomActivity() {

        binding.btTryAnother.isEnabled = false
        binding.tlActivityTable.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
        binding.tvActivity.text = "Loading..."

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(APIService::class.java).getActivityByCategory(
                getUrlForRequest()
            )
            val activity : ActivityResponse? = call.body()

            getActivity()?.runOnUiThread {
                if(call.isSuccessful) {

                    activity?.error?.let {
                        Toast.makeText(context, activity.error, Toast.LENGTH_SHORT)
                            .show()
                        Log.e("E", activity.error)
                        //TODO MOVE TO OTHER FRAGMENT
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
                    Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show()
                    Log.e("E","Error en call, Error Code ${call.code()}")
                    //TODO MOVE TO OTHER FRAGMENT
                }
            }
        }
    }
    private fun getUrlForRequest(): String {
        val sharedPref = context?.getSharedPreferences("ACTIVITY", Context.MODE_PRIVATE)
        var participants = sharedPref?.getString("PARTICIPANTS", "") ?: ""
        var price = sharedPref?.getString("PRICE", "") ?: ""
        var category = "RANDOM"
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