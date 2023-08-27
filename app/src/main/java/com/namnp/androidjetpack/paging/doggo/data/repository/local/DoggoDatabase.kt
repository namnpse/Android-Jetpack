package com.namnp.androidjetpack.paging.doggo.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.namnp.androidjetpack.paging.doggo.data.model.DoggoImageModel

@Database(
    version = 1,
    entities = [DoggoImageModel::class, RemoteKey::class],
    exportSchema = false,
)
abstract class DoggoDatabase: RoomDatabase() {

    abstract val remoteKeyDao: RemoteKeyDao
    abstract val doggoDao: DoggoDao

    companion object {
        val DOGGO_DB = "doggo_db"

        @Volatile
        private var INSTANCE: DoggoDatabase? = null
        fun getInstance(applicationContext: Context): DoggoDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    applicationContext,
                    DoggoDatabase::class.java,
                    DOGGO_DB
                ).build()
            }
        }
    }
}