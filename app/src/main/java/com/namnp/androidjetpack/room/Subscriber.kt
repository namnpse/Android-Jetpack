package com.namnp.androidjetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "subscriber_table")
data class Subscriber(
    @ColumnInfo("subscriber_id")
    val id: Int,
    @ColumnInfo("subscriber_name")
    val name: String,
    @ColumnInfo("subscriber_email")
    val email: String,
)
