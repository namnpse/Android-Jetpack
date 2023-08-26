package com.namnp.androidjetpack.recycler_view.diff_util

import androidx.recyclerview.widget.DiffUtil

class UserDiffUtilCallback(
    private val oldList: List<User>,
    private val newList: List<User>,
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                || oldList[oldItemPosition].name == newList[newItemPosition].name
//        return when {
//            oldList[oldItemPosition].id == newList[newItemPosition].id -> true
//            oldList[oldItemPosition].name == newList[newItemPosition].name -> true
//            else -> false
//        }
    }
}