package com.kennygunderman.ombrello.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kennygunderman.ombrello.BR

abstract class BaseRecyclerViewAdapter : RecyclerView.Adapter<ObjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ObjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
        val obj = getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    abstract fun getObjForPosition(position: Int): Any

    abstract fun getLayoutIdForPosition(position: Int): Int
}

class ObjectViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: Any) {
        binding.setVariable(BR.viewModel, obj)
        binding.executePendingBindings()
    }
}