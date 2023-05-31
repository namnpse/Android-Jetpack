package com.namnp.androidjetpack.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding
    // 2 ways of init a view model
    // C1
    private lateinit var viewModel: CountViewModel
    // C2: using extension
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)
        viewModelFactory = CustomViewModelFactory(startingTotal = 10)
        // C1
        viewModel = ViewModelProvider(this, viewModelFactory)[CountViewModel::class.java]

        // Ex 1
//        binding.tvCount.text = viewModel.getCount().toString()
//        binding.submitButton.setOnClickListener {
//            binding.tvCount.text = viewModel.getUpdatedCount().toString()
//        }

        // Ex 2
        // create custom view model by using View Model Factory to pass arguments to view model
        // if pass arguments directly without using View Model factory -> error
        binding.tvCount.text = viewModel.getTotal().toString()
        binding.submitButton.setOnClickListener {
            viewModel.add(binding.edtNumber.text.toString().toInt())
            binding.tvCount.text = viewModel.getTotal().toString()
        }


    }
}