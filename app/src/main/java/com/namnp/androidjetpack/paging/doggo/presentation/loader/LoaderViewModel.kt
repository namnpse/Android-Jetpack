package com.namnp.androidjetpack.paging.doggo.presentation.loader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import kotlinx.coroutines.flow.Flow

class LoaderViewModel(
    private val repository: DoggoRepository = DoggoRepository.getInstance()
): ViewModel() {

    fun getDoggoImage(): Flow<PagingData<DoggoImageModel>> {
//        viewModelScope.launch {
//            repository.getDoggoFlow()
//        }
        return repository.getDoggoFlow().cachedIn(viewModelScope)
    }
}