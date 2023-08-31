package com.namnp.androidjetpack.paging.doggo.presentation.local

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.namnp.androidjetpack.R

class LocalDoggoAdapter :
    PagingDataAdapter<String, LocalDoggoAdapter.DoggoImageViewHolder>(diffCallback = DIFF_UTIL) {

    private object DIFF_UTIL : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doggo_image_view, parent, false)
        return DoggoImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoggoImageViewHolder, position: Int) {
        getItem(position)?.let { url ->
            holder.bind(url)
        }
    }

    inner class DoggoImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivDoggo: ImageView = view.findViewById(R.id.ivDoggoMain)

        fun bind(imageUrl: String) {
            ivDoggo.load(imageUrl) {
                placeholder(R.drawable.dog_placeholder)
            }
        }
    }
}