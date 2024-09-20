package com.example.taskeffectivemobile

import android.util.Log

/*Task 4
Написать шейкерную сортировку для List<Int?>. Учесть кейсы, когда переданный массив = null,
некоторые эл-ты массива = null - пушить такие эл-ты в конец сортированного списка.*/

val listForShSort = mutableListOf(2, 4, 5, 1, 7, null, 11, 9, 19, 17, 22, 3, null)

fun shakerSorting(list: MutableList<Int?>?) {
    if (list.isNullOrEmpty()) {
        Log.d("shSort", "List empty")
    } else {
        val size = list.size
        for (pass in 0 until size) {

            for (currentIndex in 0 until size - pass - 1) {
                val currentElement = list[currentIndex]
                val nextElement = list[currentIndex + 1]
                if (currentElement != null && nextElement != null) {
                    if (currentElement > nextElement) {
                        swap(list, currentIndex, currentIndex + 1)
                    }
                } else if (currentElement == null && nextElement != null) {
                    swap(list, currentIndex, currentIndex + 1)
                }
            }

            for (currentIndex in size - 1 downTo 0 + 1) {
                val currentElement = list[currentIndex]
                val previousElement = list[currentIndex - 1]
                if (currentElement != null && previousElement != null) {
                    if (previousElement > currentElement) {
                        swap(list, currentIndex, currentIndex - 1)
                    }
                } else if (previousElement == null && currentElement != null) {
                    swap(list, currentIndex, currentIndex - 1)
                }
            }
        }
    }
}

// Функция для обмена двух элементов списка
fun swap(list: MutableList<Int?>, firstIndex: Int, secondIndex: Int) {
    val temporary = list[firstIndex]
    list[firstIndex] = list[secondIndex]
    list[secondIndex] = temporary
}
