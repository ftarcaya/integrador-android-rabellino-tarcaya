package com.example.notbored

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.databinding.ItemListBinding

class ActivityViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListBinding.bind(view)

    fun bind(activityInPosition: String, participants : String, price : String) {
        binding.tvActivityName.text = activityInPosition
        binding.itemView.setOnClickListener {
            val intent = Intent(it.context, SummaryActivity::class.java)
            intent.putExtra("ACTIVITY",activityInPosition)
            intent.putExtra("PARTICIPANTS",participants)
            intent.putExtra("PRICE",price)
            it.context.startActivity(intent)
        }
    }
}
