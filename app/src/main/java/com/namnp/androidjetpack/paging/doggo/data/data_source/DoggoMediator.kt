package com.namnp.androidjetpack.paging.doggo.data.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository.Companion.DEFAULT_PAGE_INDEX
import com.namnp.androidjetpack.paging.doggo.data.repository.local.DoggoDatabase
import com.namnp.androidjetpack.paging.doggo.data.repository.local.RemoteKey
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.DoggoApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class DoggoMediator(
    private val doggoApiService: DoggoApiService,
    private val doggoDatabase: DoggoDatabase,
) : RemoteMediator<Int, DoggoImageModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DoggoImageModel>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {
            val response = doggoApiService.getDoggoImages(page, state.config.pageSize)
            val isReachEndOfList = response.isEmpty()
            doggoDatabase.withTransaction {
                // clear data
                if (loadType == LoadType.REFRESH) {
                    doggoDatabase.remoteKeyDao.clearAllRemoteKeys()
                    doggoDatabase.doggoDao.clearAllDoggos()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isReachEndOfList) null else page + 1
                val keys = response.map { doggo ->
                    RemoteKey(id = doggo.id, prevKey = prevKey, nextKey = nextKey)
                }
                doggoDatabase.remoteKeyDao.insertAll(keys)
                doggoDatabase.doggoDao.insertAll(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isReachEndOfList)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, DoggoImageModel>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getClosetRemoteKey(state)
                remoteKey?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }

            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, DoggoImageModel>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { doggo -> doggoDatabase.remoteKeyDao.remoteKeyId(doggo.id) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, DoggoImageModel>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { doggo -> doggoDatabase.remoteKeyDao.remoteKeyId(doggo.id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosetRemoteKey(state: PagingState<Int, DoggoImageModel>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                doggoDatabase.remoteKeyDao.remoteKeyId(id)
            }
        }
    }
}