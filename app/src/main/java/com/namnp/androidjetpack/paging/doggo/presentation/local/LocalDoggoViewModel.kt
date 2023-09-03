package com.namnp.androidjetpack.paging.doggo.presentation.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDoggoViewModel @Inject constructor(
    val repository: DoggoRepository
): ViewModel() {

    fun fetchDoggoImages(): Flow<PagingData<DoggoImageModel>> {
        return repository.getLocalDoggoFlow().cachedIn(viewModelScope)
    }
}