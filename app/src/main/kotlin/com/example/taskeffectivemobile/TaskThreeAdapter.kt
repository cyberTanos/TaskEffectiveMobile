package com.example.taskeffectivemobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskeffectivemobile.TaskThreeAdapter.VH
import com.example.taskeffectivemobile.databinding.ItemBinding
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TaskThreeAdapter : ListAdapter<Person, VH>(Differ) {

    val behaviorSubject = BehaviorSubject.createDefault(0)

    inner class VH(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, position: Int) {
            binding.name.text = person.name
            binding.root.setOnClickListener {
                behaviorSubject.onNext(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflate, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position)
    }

    object Differ : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}
