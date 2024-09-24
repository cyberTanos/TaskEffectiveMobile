package com.example.taskeffectivemobile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.taskeffectivemobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val time by TimeCashDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        time
        shakerSorting(listForShSort)

        binding.button.setOnClickListener {
            Log.d("ExtentionForListLog1", "${list.searchInt()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
