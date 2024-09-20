package com.example.taskeffectivemobile

/*Task - 3
Написать extention-функцию для List, которая в рантайме будет искать Int в списке типа Any и возвращать его.
Заранее подготовить список, наполненный разными классами(5-10 шт будет достаточно).
По нажатию на кнопку выводить результат в логи (не использовать рефлексию).*/

val list: List<Any> = listOf("one", 4.5f, 4, 7, 9, 2L, true)

fun List<Any>.searchInt(): Int? {
    this.forEach {
        if (it is Int) return it
    }
    return null
}
