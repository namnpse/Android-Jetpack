package com.namnp.androidjetpack.paging.doggo.presentation.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel

class LoaderDoggoAdapter :
    PagingDataAdapter<DoggoImageModel, LoaderDoggoAdapter.DoggoImageViewHolder>(diffCallback = DIFF_UTIL) {

    private object DIFF_UTIL : DiffUtil.ItemCallback<DoggoImageModel>() {
        override fun areItemsTheSame(oldItem: DoggoImageModel, newItem: DoggoImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DoggoImageModel,
            newItem: DoggoImageModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doggo_image_view, parent, false)
        return DoggoImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoggoImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it.url)
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