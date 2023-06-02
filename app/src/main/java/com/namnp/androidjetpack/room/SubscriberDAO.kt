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
//    fun getAllSubscribers(): LiveData<List<Subscriber>>
    fun getAllSubscribers(): Flow<List<Subscriber>>
    // prefer using Flow
    /*
    WHY:
    LiveData Library is a part of Android Framework. Flow is a part of Kotlin Language.
     + When we are building KMM(Kotlin Multiplatform Mobile) applications for both Android and IOS
    -> it is required to keep as much as possible codes in pure Kotlin, so we can share them between both platform specific code bases.

     + If we use Flow, we will be able to use repository and other data source classes(data layer) written in Kotlin as a part of the code base of IOS app.
     */

}