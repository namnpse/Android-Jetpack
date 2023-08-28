package com.namnp.androidjetpack.recycler_view.diff_util

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R

class RecyclerViewUsingDiffUtilActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val users = listOf<User>(
        User(1, "User1", "location1", "image"),
        User(2, "User2", "location2", "image"),
        User(3,"User3","location3","image"),
        User(4,"User4","location4","image"),
        User(5,"User5","location5","image")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_using_diff_util)

        val userAdapter = UserAdapter(users.toMutableList())
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = userAdapter

        val btnSortDescending: Button = findViewById(R.id.btnSortDescending)
        val btnSortAscending: Button = findViewById(R.id.btnAscending)

        btnSortDescending.setOnClickListener {
            val updateListUsers = users.sortedByDescending { it.id }
            userAdapter.updateListUsers(updateListUsers)
        }

        btnSortAscending.setOnClickListener {
            val updateListUsers = users.sortedBy { it.id }
            userAdapter.submitList(updateListUsers) // Way 1, update internally, don't need to declare user list in UserAdapter
//            userAdapter.updateListUsers(updateListUsers) // Way 2
        }
    }
}