package com.namnp.androidjetpack.paging.doggo.presentation.loader

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoggoLoaderFragment: Fragment(R.layout.fragment_doggo_loader) {

    lateinit var rvDoggoLoader: RecyclerView
    lateinit var loaderViewModel: LoaderViewModel
    lateinit var adapter: LoaderDoggoAdapter
    lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        getDoggoImages()
    }

    private fun initViews(view: View) {
//        loaderViewModel = defaultViewModelProviderFactory.create(LoaderViewModel::class.java)
        loaderViewModel = ViewModelProvider(this)[LoaderViewModel::class.java]
        adapter = LoaderDoggoAdapter()
        loaderStateAdapter = LoaderStateAdapter {
            adapter.retry()
        }

        rvDoggoLoader = view.findViewById(R.id.rvDoggoLoader)
        rvDoggoLoader.layoutManager = GridLayoutManager(context, 2)
        rvDoggoLoader.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    private fun getDoggoImages() {
        lifecycleScope.launch {
            loaderViewModel.getDoggoImage()
                .distinctUntilChanged()
                .collectLatest {
                    adapter.submitData(it)
            }
        }
    }
}