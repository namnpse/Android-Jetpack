package com.namnp.androidjetpack.paging.doggo.presentation.local

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DoggoLocalFragment: Fragment(R.layout.fragment_doggo_local) {

    private lateinit var rvDoggoRoom: RecyclerView
    lateinit var localViewModel: LocalDoggoViewModel
    lateinit var adapter: LocalDoggoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchDoggoImages()
    }

    private fun fetchDoggoImages() {
        lifecycleScope.launch {
            localViewModel.fetchDoggoImages()
                .distinctUntilChanged()
                .map { it.map { doggo -> doggo.url } }
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun initMembers() {
//        localViewModel = defaultViewModelProviderFactory.create(LocalDoggoViewModel::class.java)
        localViewModel = ViewModelProvider(this)[LocalDoggoViewModel::class.java]
        adapter = LocalDoggoAdapter()
    }

    private fun setUpViews(view: View) {
        rvDoggoRoom = view.findViewById(R.id.rvDoggoRoom)
        rvDoggoRoom.layoutManager = GridLayoutManager(context, 2)
    }
}