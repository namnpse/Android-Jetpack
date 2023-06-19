package com.namnp.androidjetpack.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class SubscriberDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
//    Since testing Architecture components, To execute those tasks synchronously,
//    need to use InstantTaskExecutorRule.

    private lateinit var dao: SubscriberDAO
    private lateinit var database: SubscriberDatabase

//    allows to create temporary databases for testing.
//    The database will be created in memory
//    When we kill the process after testing the app, database will be removed and data will not be persisted.

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SubscriberDatabase::class.java,
        ).build()
        dao = database.subscriberDAO
    }

    @Test
    fun saveFunc_listSubscribers_saveSuccessfully() = runBlocking {
        val givenSubscribers = listOf<Subscriber>(
            Subscriber(
                id = 1,
                name = "name1",
                email = "email1",
                phone = "phone1",
                address = "address1"
            ),
            Subscriber(
                id = 2,
                name = "name2",
                email = "email2",
                phone = "phone2",
                address = "address2"
            ),
            Subscriber(
                id = 3,
                name = "name3",
                email = "email3",
                phone = "phone3",
                address = "address3"
            ),
        )
        dao.insertList(givenSubscribers)
        dao.getAllSubscribers().collectLatest {
            val allSubscribers = it
            Truth.assertThat(allSubscribers).isEqualTo(givenSubscribers)
        }
    }

    @Test
    fun deleteFunc_listSubscribers_deleteSuccessfully() = runBlocking {
        val givenSubscribers = listOf<Subscriber>(
            Subscriber(
                id = 1,
                name = "name1",
                email = "email1",
                phone = "phone1",
                address = "address1"
            ),
            Subscriber(
                id = 2,
                name = "name2",
                email = "email2",
                phone = "phone2",
                address = "address2"
            ),
            Subscriber(
                id = 3,
                name = "name3",
                email = "email3",
                phone = "phone3",
                address = "address3"
            ),
        )
        dao.insertList(givenSubscribers)
        dao.deleteAll()
        dao.getAllSubscribers().collectLatest {
            val allSubscribers: List<Subscriber> = it
            Truth.assertThat(allSubscribers).isEmpty()
        }
    }

    @After
    fun tearDown() {
        database.close()
    }
}