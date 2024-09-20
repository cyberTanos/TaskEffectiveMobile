package com.example.taskeffectivemobile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.taskeffectivemobile.databinding.ActivityMainBinding
import com.example.taskeffectivemobile.taskTest.factorialNumber
import com.example.taskeffectivemobile.taskTest.primeNumbers
import com.example.taskeffectivemobile.taskTest.primeNumbers2
import com.example.taskeffectivemobile.taskTest.printNumbers
import com.example.taskeffectivemobile.taskTest.sumNumber
import com.example.taskeffectivemobile.taskTest.tableMultiplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val time by TimeCashDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shakerSorting(listForShSort)

        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                Log.d("TimeCashDelegateLog", time)
            }
        }

        binding.button.setOnClickListener {
            Log.d("ExtentionForListLog1", "${list.searchInt()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
