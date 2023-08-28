package com.namnp.androidjetpack.paging.doggo.presentation.remote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.namnp.androidjetpack.R

//class RemoteDoggoAdapter: ListAdapter<String, RemoteDoggoAdapter.DoggoViewHolder>(DIFF_UTIL) {
class RemoteDoggoAdapter: PagingDataAdapter<String, RemoteDoggoAdapter.DoggoViewHolder>(DIFF_UTIL) {

    private object DIFF_UTIL : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doggo_image_view, parent, false)
        return DoggoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoggoViewHolder, position: Int) {
        getItem(position)?.let { url ->
            holder.bind(imageUrl = url)
        }
//        (holder as? DoggoViewHolder)?.bind(imageUrl = getItem(position)!!)
    }

    inner class DoggoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivDoggo: ImageView = view.findViewById(R.id.ivDoggoMain)

        fun bind(imageUrl: String) {
            ivDoggo.load(imageUrl) {
                placeholder(R.drawable.dog_placeholder)
            }
        }
    }

}