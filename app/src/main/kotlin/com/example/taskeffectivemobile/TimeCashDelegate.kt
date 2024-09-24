package com.example.taskeffectivemobile

import android.util.Log
import androidx.lifecycle.lifecycleScope
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Task 2 - Delegate

class TimeCashDelegate : ReadWriteProperty<MainActivity?, String> {

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    override fun getValue(thisRef: MainActivity?, property: KProperty<*>): String {
        thisRef?.lifecycleScope?.launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                Log.d("TimeCashDelegateLog", currentDate)
            }
        }
        return currentDate
    }

    override fun setValue(thisRef: MainActivity?, property: KProperty<*>, value: String) = Unit
}
