package com.namnp.androidjetpack.design_pattern

// encapsulate a group of interchangeable algorithms and use them interchangeably
// dynamically selecting the appropriate algorithm at runtime

interface SortStrategy {
    fun sort(data: List<Int>): List<Int>
}

class BubbleSortStrategy: SortStrategy {
    override fun sort(data: List<Int>): List<Int> {
        // implement bubble sort
        TODO("Not yet implemented")
    }
}

class QuickSortStrategy: SortStrategy {
    override fun sort(data: List<Int>): List<Int> {
        // implement quick sort
        TODO("Not yet implemented")
    }
}

class Sorter(private val strategy: SortStrategy) {
    fun sortData(data: List<Int>): List<Int> {
        return strategy.sort(data)
    }
}

fun main() {
    val data = listOf(3,6,7,1,2)
//    val sorter = Sorter(BubbleSortStrategy())
    val sorter = Sorter(QuickSortStrategy())
    val sortedData = sorter.sortData(data)
}