package com.namnp.androidjetpack.testing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.databinding.ActivityCalculationBinding

class CalculationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculationBinding
    private lateinit var viewModel: CalcViewModel
    lateinit var factory: CalcViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation)
        factory = CalcViewModelFactory(CalculationImpl())
        viewModel = ViewModelProvider(this, factory)
            .get(CalcViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }
}

// NOTE
//1. Testing pyramid: 70-20-10
//2. Test doubles (Fake, Stub, Mock)
//      2.1 Fake:
//          + a light weight implementation class of the Interface,
//          + usually we hand code fake classes. So the Subject under test can use the fake instead of the real one.

//      2.2: Stub is an object that provides predefined return values to method calls.
//      2.3:
//          + Mock is similar to stub, but they allows tester to set answers to method calls when writing the test case.
//          + That means In mocks we dynamically set expected return values for the method calls.
//          + Not like fakes, we usually generate stubs and mocks using a testing framework.
//3. Naming Test function by pattern: ObjectUnderTest_givenInput_action