package com.namnp.androidjetpack.paging.doggo.data.repository.remote

import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel
import retrofit2.http.Query

interface DoggoApiService {

    fun getDoggoImages(
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ) : List<DoggoImageModel>
}