package com.mlkit.sample.apicallsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlkit.sample.apicallsample.R
import com.mlkit.sample.apicallsample.models.ApiData

class MainActivityAdapter(var list: List<ApiData>) :
    RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterViewHolder>() {
    inner class MainActivityAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityAdapterViewHolder = MainActivityAdapterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.api_data_item, parent, false)
    )

    override fun onBindViewHolder(holder: MainActivityAdapterViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.userId).text = list[position].userId
        holder.itemView.findViewById<TextView>(R.id.id).text = list[position].id
        holder.itemView.findViewById<TextView>(R.id.title).text = list[position].title
        holder.itemView.findViewById<TextView>(R.id.body).text = list[position].body
    }

    override fun getItemCount(): Int = list.size
}