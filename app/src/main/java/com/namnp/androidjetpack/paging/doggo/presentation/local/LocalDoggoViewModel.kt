package com.namnp.androidjetpack.paging.doggo.presentation.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import kotlinx.coroutines.flow.Flow

class LocalDoggoViewModel(
    val repository: DoggoRepository = DoggoRepository.getInstance()
): ViewModel() {

    fun fetchDoggoImages(): Flow<PagingData<DoggoImageModel>> {
        return repository.getLocalDoggoFlow().cachedIn(viewModelScope)
    }
}