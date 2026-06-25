package com.example.kursath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private var dataList: List<Model>,
    private val onItemClick: (Model) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    fun updateData(newList: List<Model>) {
        dataList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvCol1.text = item.col1
        holder.tvCol2.text = item.col2
        holder.tvCol3.text = "${item.col3} | ${item.col4}"

        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCol1: TextView = itemView.findViewById(R.id.tvCol1)
        val tvCol2: TextView = itemView.findViewById(R.id.tvCol2)
        val tvCol3: TextView = itemView.findViewById(R.id.tvCol3)
    }
}