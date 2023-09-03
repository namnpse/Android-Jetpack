package com.namnp.androidjetpack.paging.doggo.presentation.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDoggoViewModel @Inject constructor(
    val repository: DoggoRepository
): ViewModel() {

    /**
     * map the data received from the repository to [PagingData<String>] to show the map
     * function you can always return the original model if needed, in our case it would be [DoggoImageModel]
     */
    fun getFlowDoggoList(): Flow<PagingData<String>> {
        return repository.getDoggoFlow()
            .map { it.map { doggo -> doggo.url } }
            .cachedIn(viewModelScope)
    }

    fun getLiveDataDoggoList(): LiveData<PagingData<String>> {
        return repository.getDoggoLiveData()
            .map { it.map { doggo -> doggo.url } }
            .cachedIn(viewModelScope)
    }

    fun getObservableDoggoList(): Observable<PagingData<String>> {
        return repository.getDoggoRxJava()
            .map { it.map { doggo -> doggo.url } }
            .cachedIn(viewModelScope)
    }
}