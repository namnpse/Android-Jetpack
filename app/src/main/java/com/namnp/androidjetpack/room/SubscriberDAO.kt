package com.namnp.androidjetpack.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscriber: Subscriber) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(subscriber: List<Subscriber>) : List<Long>

    @Update
    suspend fun update(subscriber: Subscriber) : Int

    @Delete
    suspend fun delete(subscriber: Subscriber) : Int

    @Query("DELETE FROM subscriber_table")
    suspend fun deleteAll() : Int

    // don't need to use suspend fun, Room auto do it in background thread
    @Query("SELECT * FROM subscriber_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
//    fun getAllSubscribers(): Flow<List<Subscriber>> // prefer using Flow
}