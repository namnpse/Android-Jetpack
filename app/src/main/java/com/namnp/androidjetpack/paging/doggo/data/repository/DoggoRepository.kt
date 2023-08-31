package com.namnp.androidjetpack.paging.doggo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.rxjava2.observable
import com.namnp.androidjetpack.paging.doggo.data.data_source.DoggoMediator
import com.namnp.androidjetpack.paging.doggo.data.data_source.DoggoPagingSource
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.local.DoggoDatabase
import com.namnp.androidjetpack.paging.doggo.data.repository.local.LocalInjector
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.DoggoApiService
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.RemoteInjector
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class DoggoRepository(
    val doggoApiService: DoggoApiService = RemoteInjector.injectDoggoApiService(),
    val appDatabase: DoggoDatabase? = LocalInjector.injectDb()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        //get doggo repository instance
        fun getInstance() = DoggoRepository()
    }

    private val pageConfig = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)

    // fof Flow
    fun getDoggoFlow(): Flow<PagingData<DoggoImageModel>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { DoggoPagingSource(doggoApiService) }
        ).flow
    }

    // for LiveData
    fun getDoggoLiveData(): LiveData<PagingData<DoggoImageModel>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { DoggoPagingSource(doggoApiService) }
        ).liveData
    }

    // for RxJava
    fun getDoggoRxJava(): Observable<PagingData<DoggoImageModel>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { DoggoPagingSource(doggoApiService) }
        ).observable
    }

    private val defaultPageConfig by lazy { PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true) }

    fun getLocalDoggoFlow(pagingConfig: PagingConfig = defaultPageConfig): Flow<PagingData<DoggoImageModel>> {
        if (appDatabase == null) throw IllegalStateException("Database is not initialized")

        val pagingSourceFactory = { appDatabase.doggoDao.getAllDoggoModel() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = DoggoMediator(doggoApiService, appDatabase)
        ).flow
    }

}