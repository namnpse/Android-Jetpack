package com.namnp.androidjetpack.paging.doggo.presentation.loader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoaderViewModel @Inject constructor(
    private val repository: DoggoRepository
): ViewModel() {

    fun getDoggoImage(): Flow<PagingData<DoggoImageModel>> {
//        viewModelScope.launch {
//            repository.getDoggoFlow()
//        }
        return repository.getDoggoFlow().cachedIn(viewModelScope)
    }
}