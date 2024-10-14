package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val context: Context, private val list: ArrayList<MainActivity.ColorData>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorName: TextView = view.findViewById(R.id.item_title)
        val colorView: View = view.findViewById(R.id.color_box)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorData = list[position]
        holder.colorName.text = colorData.colorName
        holder.colorView.setBackgroundColor(colorData.colorHex)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(colorData)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
