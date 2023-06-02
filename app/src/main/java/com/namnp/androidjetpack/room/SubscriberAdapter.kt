package com.namnp.androidjetpack.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.SubscriberItemBinding

class SubscriberAdapter(
    private val onClickListener: (Subscriber) -> Unit
) : RecyclerView.Adapter<SubscriberViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()

    fun setData(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding: SubscriberItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.subscriber_item, parent, false)
        val binding: SubscriberItemBinding = SubscriberItemBinding.inflate(layoutInflater) // more concise way
        return SubscriberViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribersList[position], onClickListener)
    }
}

class SubscriberViewHolder(val binding: SubscriberItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}