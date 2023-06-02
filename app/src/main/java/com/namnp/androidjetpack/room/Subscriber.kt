package com.namnp.androidjetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("subscriber_id")
    val id: Int,
    @ColumnInfo("subscriber_name")
    var name: String,
    @ColumnInfo("subscriber_email")
    var email: String,
)
