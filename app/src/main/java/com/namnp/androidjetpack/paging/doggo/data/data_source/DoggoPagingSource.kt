package com.namnp.androidjetpack.paging.doggo.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository.Companion.DEFAULT_PAGE_INDEX
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.DoggoApiService
import retrofit2.HttpException
import java.io.IOException

class DoggoPagingSource(
    private val doggoApiService: DoggoApiService
): PagingSource<Int, DoggoImageModel>() {
    override fun getRefreshKey(state: PagingState<Int, DoggoImageModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoggoImageModel> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = doggoApiService.getDoggoImages(page, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if(page == 1) null else page - 1,
                nextKey = if(response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}