package com.namnp.androidjetpack.paging.doggo.presentation.remote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DoggoRemoteFragment : Fragment(R.layout.fragment_doggo_remote) {

    lateinit var rvRemoteDoggo: RecyclerView
    lateinit var remoteDoggoViewModel: RemoteDoggoViewModel
    lateinit var doggoAdapter: RemoteDoggoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getFlowDoggo()
//        getLiveDataDoggo()
//        getObservableDoggo()
    }

    private fun getFlowDoggo() {
        lifecycleScope.launch {
            remoteDoggoViewModel.getFlowDoggoList()
                .distinctUntilChanged()
                .collectLatest {
//                doggoAdapter.submitList(it) // for ListAdapter
                    doggoAdapter.submitData(it) // for PagingAdapter
                }
        }
    }

    private fun getLiveDataDoggo() {
        remoteDoggoViewModel.getLiveDataDoggoList()
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    doggoAdapter.submitData(it)
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun getObservableDoggo() {
        remoteDoggoViewModel.getObservableDoggoList()
            .distinctUntilChanged()
            .subscribe {
                lifecycleScope.launch {
                    doggoAdapter.submitData(it)
                }
            }
    }

    private fun init(view: View) {
        remoteDoggoViewModel =
            defaultViewModelProviderFactory.create(RemoteDoggoViewModel::class.java) // Java style
        remoteDoggoViewModel =
            ViewModelProvider(this)[RemoteDoggoViewModel::class.java] // Kotlin style
        doggoAdapter = RemoteDoggoAdapter()
        rvRemoteDoggo = view.findViewById<RecyclerView>(R.id.rvDoggoRemote).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = doggoAdapter
        }
    }
}