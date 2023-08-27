package com.namnp.androidjetpack.paging.doggo.data.repository.local

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel

interface DoggoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(doggoModel: List<DoggoImageModel>)

    @Query("SELECT * FROM doggoimagemodel")
    fun getAllDoggoModel(): PagingSource<Int, DoggoImageModel>

    @Query("DELETE FROM doggoimagemodel")
    suspend fun clearAllDoggos()
}
