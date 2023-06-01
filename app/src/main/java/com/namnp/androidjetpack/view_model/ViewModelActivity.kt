package com.namnp.androidjetpack.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewModelBinding

// SUMMARY

/*
when a configuration change like screen rotation happens,
app has to destroy and recreate the activity or fragment with new configurations.

As a result of that, values(states) created during the running period of the activity will also be destroyed.

-> The Android Jetpack View Model architecture component has introduced as a solution for above problem.
We can use a view model object to safely keep and retrieve values(states) of that activity.

As its name says, view model is a model for a view. We usually create a view model for each activity.
A ViewModel’s onCleared() is called:
 + when the app is put into the background and the app process is killed in order to free up the system’s memory.
 + user invokes finish() or click back
Therefore, lifecycle changes of activities and fragments does not affect to their ViewModels.
(Activities and fragments may destroy and recreate, but view model will live throughout the process)
*/
/* 2 ways of init a view model
 C1: using ViewModelProvider (with/without ViewModelFactory)
 C2: using extension*/

//ViewModelProvider can only instantiate ViewModels with no arg constructors.
// -> if the ViewModel has constructor parameters(arguments) , ViewModelProvider need a little extra support to create instances of it (ViewModelFactory)

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding
    // 3 ways of init a view model
    // C1
    private lateinit var viewModel: CountViewModel
    // C2: using extension
    // val viewModel by viewModels<MyViewModel>()
//    For Activities.
//    implementation 'androidx.activity:activity-ktx:1.3.1'
//    For Fragments
//    implementation 'androidx.fragment:fragment-ktx:1.3.1'

    // Get a reference to the ViewModel scoped to this Fragment
    //    val viewModel by viewModels<MyViewModel>()
    // Get a reference to the ViewModel scoped to its Activity
    //    val viewModel by activityViewModels<MyViewModel>()
    private val viewModelUsingExtension: CountViewModel by viewModels()
    private lateinit var viewModelFactory: CustomViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // C2
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)
        // C3
//        binding = ActivityViewModelBinding.inflate(layoutInflater)
        viewModelFactory = CustomViewModelFactory(startingTotal = 10)
        viewModel = ViewModelProvider(this, viewModelFactory)[CountViewModel::class.java]
        binding.viewModel = viewModel

        // Ex 1
//        binding.tvCount.text = viewModel.getCount().toString()
//        binding.submitButton.setOnClickListener {
//            binding.tvCount.text = viewModel.getUpdatedCount().toString()
//        }

        // Ex 2
        // create custom view model by using View Model Factory to pass arguments to view model
        // if pass arguments directly without using View Model factory -> error
//        binding.tvCount.text = viewModel.getTotal().toString()
        binding.submitButton.setOnClickListener {
            if(binding.edtNumber.text.toString().isEmpty()) return@setOnClickListener
            viewModel.add(binding.edtNumber.text.toString().toInt())
//            binding.tvCount.text = viewModel.getTotal().toString()
        }

        // Ex 3: using live data
        //  RxJava >< LiveData:
        /*RxJava:
        + is not a lifecycle aware component.
        -> data stream does not go off (when activity, fragment or service becomes inactive).
        + As a result of that, memory leaks or crashes can happen.
        + Therefore, we have to write codes to dispose them manually.
        LiveData:
        + Android LiveData aware of lifecycle status changes.
        + clean up themselves(stop emitting data) when their associated lifecycle is destroyed.*/
//        viewModel.totalLiveData.observe(this) {
//            binding.tvCount.text = it.toString()
//        }

/*      Ex 4: binding + view model + live data
        dont need to use, all is binding in xml file
        viewModel.totalLiveData.observe(this) {
            binding.tvCount.text = it.toString()
        }
        but MUST to add this line
        */
        binding.lifecycleOwner = this
    }
}