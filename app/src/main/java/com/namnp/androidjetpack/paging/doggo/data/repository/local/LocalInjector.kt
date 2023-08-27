package com.namnp.androidjetpack.paging.doggo.data.repository.local

object LocalInjector {

    var appDatabase: DoggoDatabase? = null

    fun injectDb(): DoggoDatabase? {
        return appDatabase
    }
}