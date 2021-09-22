package com.example.notbored.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.Adapters.ActivityAdapter
import com.example.notbored.databinding.FragmentSelectorBinding

class SelectorFragment : Fragment() {

    private lateinit var homeViewModel: SelectorViewModel
    private var _binding: FragmentSelectorBinding? = null
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


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(SelectorViewModel::class.java)

        _binding = FragmentSelectorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    private fun initRV() {
        val sharedPref = context?.getSharedPreferences("ACTIVITY", Context.MODE_PRIVATE)
        var participants = sharedPref?.getString("PARTICIPANTS", "") ?: ""
        var price = sharedPref?.getString("PRICE", "") ?: ""
        binding.lvActivities.layoutManager = LinearLayoutManager(context)
        binding.lvActivities.adapter = ActivityAdapter(listActivities,participants,price)
        binding.lvActivities.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}