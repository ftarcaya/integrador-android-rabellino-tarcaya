package com.example.notbored

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.databinding.ItemListBinding

class ActivityViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListBinding.bind(view)

    fun bind(activityInPosition: String) {
        binding.tvActivityName.text = activityInPosition
        binding.itemView.setOnClickListener {
            val intent = Intent(it.context, ActivityActivity::class.java)
            intent.putExtra("ACTIVITY",activityInPosition)
            it.context.startActivity(intent)
        }
    }
}
