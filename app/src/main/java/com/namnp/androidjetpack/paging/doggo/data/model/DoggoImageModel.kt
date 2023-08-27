package com.namnp.androidjetpack.paging.doggo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoggoImageModel(
    @PrimaryKey
    val id: String,
    val url: String,
)