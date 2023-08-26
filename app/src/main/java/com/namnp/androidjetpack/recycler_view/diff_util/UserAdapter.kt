package com.namnp.androidjetpack.recycler_view.diff_util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.namnp.androidjetpack.R

class UserAdapter(
    private val users: MutableList<User>,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun updateListUsers(newList: List<User>) {
        val diffUsers = DiffUtil.calculateDiff(UserDiffUtilCallback(users, newList))
        users.clear()
        users.addAll(newList)
        diffUsers.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.onBind(user)
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val username: AppCompatTextView = view.findViewById(R.id.username)
        private val address: AppCompatTextView = view.findViewById(R.id.address)

        fun onBind(user: User) {
            username.text = user.name
            address.text = user.address
        }
    }
}