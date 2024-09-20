package com.example.taskeffectivemobile

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Task 2 - Delegate

class TimeCashDelegate : ReadWriteProperty<Any?, String> {

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return currentDate
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) = Unit
}
