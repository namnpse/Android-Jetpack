package com.namnp.androidjetpack.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityRoomBinding

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    // C1: extension
//    private val subscriberViewModel: SubscriberViewModel by viewModels() (if is has no params in constructor)
    // C2:
    private lateinit var subscriberViewModel: SubscriberViewModel

    private lateinit var adapter: SubscriberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // C1:
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        // C2:
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer { message ->
            message.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SubscriberAdapter { selectedItem: Subscriber ->
            listItemClicked(selectedItem)
        }
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.getAllSubscribersViaFlow.observe(this, Observer {
            Log.i("RoomActivity", it.toString())
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}