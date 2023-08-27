package com.namnp.androidjetpack.paging.doggo.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKey>)

    @Query("SELECT * FROM remotekey WHERE id = :id")
    suspend fun remoteKeyId(id: String): RemoteKey?

    @Query("DELETE FROM remotekey")
    suspend fun clearAllRemoteKeys()


}