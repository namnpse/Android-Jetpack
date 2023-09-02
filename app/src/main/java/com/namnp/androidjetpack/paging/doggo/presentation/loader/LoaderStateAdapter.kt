package com.namnp.androidjetpack.paging.doggo.presentation.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R

class LoaderStateAdapter(private val onRetry: () -> Unit): LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doggo_loader, parent, false)
        return LoaderViewHolder(view, onRetry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoaderViewHolder(view: View, onRetry: () -> Unit): RecyclerView.ViewHolder(view) {

        private val motionLayout: MotionLayout = view.findViewById(R.id.mlLoader)
        private val btnRetry: Button = view.findViewById(R.id.btnRetry)

        init {
            btnRetry.setOnClickListener {
                onRetry()
            }
        }

        fun bind(loadState: LoadState) {
            if(loadState is LoadState.Loading) {
                motionLayout.transitionToEnd()
            }else {
                motionLayout.transitionToStart()
            }
        }
    }
}