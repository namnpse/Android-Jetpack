package com.namnp.androidjetpack.design_pattern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R

data class Data(val id: String, val value: String)


// acting as a bridge between
// used to adapt existing classes to be compatible with other classes or interfaces.
class DataAdapter(private val dataList: List<Data>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        // Inflate the item layout and return a new ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        // Bind the data to the ViewHolder
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        // Return the size of the data list
        return dataList.size
    }

    inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
        }
    }
}