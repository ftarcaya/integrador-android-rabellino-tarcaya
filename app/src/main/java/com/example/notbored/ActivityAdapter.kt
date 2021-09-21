package com.example.notbored

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(private val activities : List<String>, private val participants : String) : RecyclerView.Adapter<ActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityViewHolder(layoutInflater.inflate(R.layout.item_list,parent,false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activityInPosition = activities[position]
        holder.bind(activityInPosition,participants)
    }

    override fun getItemCount(): Int {
        return activities.size
    }


}