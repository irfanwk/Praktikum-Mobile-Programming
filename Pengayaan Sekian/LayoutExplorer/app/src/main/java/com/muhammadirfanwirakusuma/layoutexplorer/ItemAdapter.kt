package com.muhammadirfanwirakusuma.layoutexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvItemName)
        val tvDesc: TextView = view.findViewById(R.id.tvItemDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvName.text = item.name
        holder.tvDesc.text = item.desc

        // OnClickListener untuk menampilkan Toast
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih: ${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = itemList.size
}