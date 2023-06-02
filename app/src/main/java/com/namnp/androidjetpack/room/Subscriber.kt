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
    @ColumnInfo("subscriber_phone_number", defaultValue = "No phone") // phone is not null -> use default value for auto migration
    var phone: String,
    @ColumnInfo("subscriber_address") // address is nullable -> don't need to use default value for auto migration
    var address: String? = null,
)
